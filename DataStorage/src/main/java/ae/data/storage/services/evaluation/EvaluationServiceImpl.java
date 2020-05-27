package ae.data.storage.services.evaluation;

import ae.data.storage.models.cold.AnalysisData;
import ae.data.storage.models.hot.*;
import ae.data.storage.models.redis.DataMapper;
import ae.data.storage.repositories.cold.AnalysisDataRepository;
import ae.data.storage.repositories.hot.*;
import ae.data.storage.repositories.redis.DataMapperRepository;
import ae.data.storage.rowmappers.AbcResultRowMapper;
import ae.data.storage.rowmappers.CommodityGroupAnalysisResultRowMapper;
import ae.data.storage.rowmappers.ReturnEvaluationDataRowMapper;
import ae.data.storage.rowmappers.XyzRowMapper;
import ae.data.storage.utils.QueryUtils;
import lombok.SneakyThrows;
import main.dto.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvalualtionDataRepository evalualtionDataRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private DataMapperRepository dataMapperRepository;

    @Autowired
    private CommodityGroupRepository commodityGroupRepository;

    @Autowired
    private AnalysisDataRepository analysisDataRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private AbcResultRepository abcResultRepository;

    @Autowired
    private XyzResultRepository xyzResultRepository;

    @Override
    @SneakyThrows
    public void saveEvaluation(MainDto mainDto) {
        EvaluationDto evaluationDto = (EvaluationDto) MainUtil.stringJsonToObject(mainDto.getJson(), EvaluationDto.class);

        Evaluation evaluation = evaluationRepository.save(Evaluation.builder()
                .facility(facilityRepository.findById(evaluationDto.getFacilityId()).get())
                .name(evaluationDto.getName())
                .startsFrom(LocalDate.parse(evaluationDto.getStartsFrom()).atStartOfDay(ZoneOffset.UTC).toInstant())
                .lastNeeded(Instant.now())
                .description(evaluationDto.getDescription())
                .build());

        dataMapperRepository.save(DataMapper.builder()
                .id(evaluationDto.getHash())
                .dataId(evaluation.getId())
                .build());
    }

    @Override
    public void saveEvaluationData(MainDto mainDto) {
        EvaluationDataDto evaluationDataDto = (EvaluationDataDto) MainUtil.stringJsonToObject(
                mainDto.getJson(),
                EvaluationDataDto.class
        );

        String hash = evaluationDataDto.getHash();

        DataMapper data = dataMapperRepository.findById(hash).get();

        Evaluation evaluation = evaluationRepository
                .findById(data.getDataId())
                .get();

        saveEvaluationData(evaluation, evaluationDataDto.getEvaluationDataRows());
        makeAbcAnalysis(evaluation.getId());
        makeXyzAnalysis(evaluation.getId());

        if (evaluationDataDto.getIsLast()) {
            dataMapperRepository.delete(data);
        }
    }

    @Override
    public void getFacilityData(GetFacilityDataDto getFacilityDataDto, String username) {
        Facility facility = facilityRepository.findById(getFacilityDataDto.getFacilityId()).get();

        List<Evaluation> evaluations = evaluationRepository.findByFacility(facility);

        List<EvaluationRowDataDto> dataRows = null;
        if (evaluations != null) {
            dataRows = evaluations.stream().map(evaluation -> {
                return EvaluationRowDataDto.builder()
                        .evaluationId(evaluation.getId())
                        .evaluationName(evaluation.getName())
                        .createdAt(evaluation.getCreated_at().toString())
                        .build();
            }).collect(Collectors.toList());
        }

        ReturnFacilityDataDto returnFacilityDataDto = ReturnFacilityDataDto.builder()
                .facilityId(facility.getId())
                .facilityName(facility.getName())
                .facilityDescription(facility.getDescription())
                .evaluations(dataRows)
                .build();

        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.RETURN_FACILITY_DATA)
                .json(MainUtil.objectToJsonString(returnFacilityDataDto))
                .user(username)
                .build();

        System.out.println(mainDto);

        rabbitTemplate.convertAndSend("dataStorageToUpdaterQueue", MainUtil.objectToByteArray(mainDto));
    }

    public void deleteEvaluation(DeleteEvaluationDto deleteEvaluationDto) {
        Evaluation evaluation = evaluationRepository.findById(deleteEvaluationDto.getEvaluationId()).get();
        evaluationRepository.delete(evaluation);
    }

    @Override
    public void getEvaluationData(GetEvaluationDataDto getEvaluationDataDto, String username) {
        Evaluation evaluation = evaluationRepository.findById(getEvaluationDataDto.getEvaluationId()).get();
        List<EvaluationData> evaluationDatas = evaluation.getEvaluationData();

        Set<Integer> periodSet = evaluationDatas
                .stream()
                .map(EvaluationData::getPeriod)
                .collect(Collectors.toCollection(TreeSet::new));

        List<ReturnEvaluationDataRow> list = new ArrayList<>();

        periodSet.forEach(value -> {
            list.add(
                    ReturnEvaluationDataRow.builder()
                            .period(evaluation
                                    .getStartsFrom()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate().plusMonths(value)
                                    .format(DateTimeFormatter.ISO_DATE))
                            .cgCount(evaluationDatas.stream().filter(val -> val.getPeriod() == value).count())
                            .periodNum(value)
                            .build()
            );
        });


        ReturnEvaluationData returnEvaluationData = ReturnEvaluationData.builder()
                .evaluationId(evaluation.getId())
                .facilityId(getEvaluationDataDto.getFacilityId())
                .evaluationName(evaluation.getName())
                .evaluationDescription(evaluation.getDescription())
                .evaluations(list)
                .build();

        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.RETURN_EVALUATION_DATA)
                .json(MainUtil.objectToJsonString(returnEvaluationData))
                .user(username)
                .build();

        rabbitTemplate.convertAndSend("dataStorageToUpdaterQueue", MainUtil.objectToByteArray(mainDto));
    }

    @Override
    public void getCommodityGroupResult(GetCommodityGroupResultDto getCommodityGroupResultDto, String user) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", getCommodityGroupResultDto.getId());

        CommodityGroupResultDto commodityGroupResultDto = namedParameterJdbcTemplate.queryForObject(
                QueryUtils.getCommodityGroupAnalysisResultQuery(),
                sqlParameterSource,
                new CommodityGroupAnalysisResultRowMapper()
        );

        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.RETURN_COMMODITY_GROUP_RESULT)
                .json(MainUtil.objectToJsonString(commodityGroupResultDto))
                .user(user)
                .build();

        rabbitTemplate.convertAndSend(
                "dataStorageToMarketAnalysisQueue",
                MainUtil.objectToByteArray(mainDto)
        );
    }

    @Override
    public void getEvaluationDataByEvaluationAndPeriod(GetEvaluationDataByEvaluationAndPeriodDto dto, String user) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("evaluation_id", dto.getEvaluationId())
                .addValue("period", dto.getPeriod());

        List<EvaluationDataRowDto> evaluationDataRows = namedParameterJdbcTemplate.query(
                QueryUtils.getEvaluationDataByPeriodAndEvaluationQuery(),
                sqlParameterSource,
                new ReturnEvaluationDataRowMapper()
        );

        Collections.sort(evaluationDataRows, new Comparator<EvaluationDataRowDto>() {
            @Override
            public int compare(EvaluationDataRowDto o1, EvaluationDataRowDto o2) {
                return extractInt(o1.getName()) - extractInt(o2.getName());
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });

        ReturnEvaluationDataByEvaluationAndPeriodDto returnDto = ReturnEvaluationDataByEvaluationAndPeriodDto.builder()
                .evaluationDataRows(evaluationDataRows)
                .build();


        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.RETURN_EVALUATION_DATA_BY_EVALUATION_AND_PERIOD)
                .json(MainUtil.objectToJsonString(returnDto))
                .user(user)
                .build();

        rabbitTemplate.convertAndSend("dataStorageToUpdaterQueue", MainUtil.objectToByteArray(mainDto));
    }

    private void saveEvaluationData(Evaluation evaluation, List<EvaluationDataRowDto> evaluationDataRows) {
        Facility facility = evaluation.getFacility();

        List<EvaluationData> evaluationData = new ArrayList<>();
        List<AnalysisData> analysisDataList = new ArrayList<>();

        for (EvaluationDataRowDto rowDto : evaluationDataRows) {
            Optional<CommodityGroup> commodityGroupOptional = commodityGroupRepository.findByNameAndFacility(rowDto.getName(), facility);
            CommodityGroup commodityGroup;
            if (commodityGroupOptional.isPresent()) {
                commodityGroup = commodityGroupOptional.get();
            } else {
                commodityGroup = CommodityGroup.builder()
                        .created_at(Instant.now())
                        .facility(facility)
                        .name(rowDto.getName())
                        .build();
                commodityGroup = commodityGroupRepository.save(commodityGroup);
            }

            evaluationData.add(EvaluationData.builder()
                    .commodityGroup(commodityGroup)
                    .evaluation(evaluation)
                    .period(rowDto.getPeriod())
                    .revenue(rowDto.getRevenue())
                    .consumption(rowDto.getConsumption())
                    .volumeOfSales(rowDto.getVolumeOfSales())
                    .build()
            );

            analysisDataList.add(AnalysisData.builder()
                    .id(UUID.randomUUID())
                    .commodityGroupId(commodityGroup.getId())
                    .consumption(rowDto.getConsumption())
                    .revenue(rowDto.getRevenue())
                    .period(rowDto.getPeriod())
                    .facilityId(facility.getId())
                    .startsFrom(evaluation.getStartsFrom())
                    .volumeOfSales((long) rowDto.getVolumeOfSales())
                    .evaluationName(evaluation.getName())
                    .evaluationDescription(evaluation.getDescription())
                    .build()
            );
        }

        analysisDataRepository.saveAll(analysisDataList);
        evalualtionDataRepository.saveAll(evaluationData);
    }

    @SneakyThrows
    private void makeAbcAnalysis(Long evaluationId) {

        Set<Integer> periods = evaluationRepository
                .findById(evaluationId)
                .get().getEvaluationData()
                .stream()
                .map(EvaluationData::getPeriod).collect(Collectors.toSet());


        periods.forEach(period -> {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("period", period)
                    .addValue("evaluation_id", evaluationId);

            List<AbcResultDto> results = namedParameterJdbcTemplate.query(
                    QueryUtils.getAbcResultsByEvaluationIdAndPeriodQuery(),
                    sqlParameterSource,
                    new AbcResultRowMapper()
            );


            for (AbcResultDto result : results) {
                EvaluationData evaluationDataFill = evalualtionDataRepository.findById(result.getCommodityGroupId()).get();
                AbcResult abcResult;
                if (evaluationDataFill.getAbcResult() != null) {
                    abcResult = evaluationDataFill.getAbcResult();
                    abcResult.setProfitPercent(result.getProfitPercent());
                    abcResult.setProfitResultPercent(result.getProfitResultPercent());
                    abcResult.setProfitResult(result.getProfitResult());
                    abcResult.setRevenueResult(result.getRevenueResult());
                    abcResult.setRevenuePercent(result.getRevenuePercent());
                    abcResult.setRevenueResultPercent(result.getRevenueResultPercent());
                    abcResult.setVolumeOfSalesResult(result.getVolumeOfSalesResult());
                    abcResult.setVolumeOfSalesPercent(result.getVolumeOfSalesPercent());
                    abcResult.setVolumeOfSalesResultPercent(result.getVolumeOfSalesResultPercent());
                } else {
                    abcResult = AbcResult.builder()
                            .revenueResult(result.getRevenueResult())
                            .revenuePercent(result.getRevenuePercent())
                            .revenueResultPercent(result.getRevenueResultPercent())
                            .profitResult(result.getProfitResult())
                            .profitPercent(result.getProfitPercent())
                            .profitResultPercent(result.getProfitResultPercent())
                            .volumeOfSalesResult(result.getVolumeOfSalesResult())
                            .volumeOfSalesPercent(result.getVolumeOfSalesPercent())
                            .volumeOfSalesResultPercent(result.getVolumeOfSalesResultPercent())
                            .evaluationData(evaluationDataFill)
                            .build();
                }

                abcResultRepository.save(abcResult);

            }
        });
    }

    @SneakyThrows
    private void makeXyzAnalysis(Long evaluationId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("evaluation_id", evaluationId);

        List<XyzResultDto> profitResults = executeXyzResultQuery(QueryUtils.getProfitXyzResultQuery(), sqlParameterSource);
        List<XyzResultDto> revenueResults = executeXyzResultQuery(QueryUtils.getRevenueXyzResultQuery(), sqlParameterSource);
        List<XyzResultDto> volumeOfSalesResults = executeXyzResultQuery(QueryUtils.getVolumeOfSalesXyzResultQuery(), sqlParameterSource);

        for (int i = 0; i < profitResults.size(); i++) {
            EvaluationData evaluationData = evalualtionDataRepository.findById(profitResults.get(i).getId()).get();
            XyzResult xyzResult;

            if (evaluationData.getXyzResult() != null) {
                xyzResult = evaluationData.getXyzResult();
            } else {
                xyzResult = new XyzResult();
                xyzResult.setEvaluationData(evaluationData);
            }

            xyzResult.setProfitPercent(profitResults.get(i).getPercent());
            xyzResult.setProfitResult(profitResults.get(i).getResult());

            xyzResult.setRevenuePercent(revenueResults.get(i).getPercent());
            xyzResult.setRevenueResult(revenueResults.get(i).getResult());

            xyzResult.setVolumeOfSalesPercent(volumeOfSalesResults.get(i).getPercent());
            xyzResult.setVolumeOfSalesResult(volumeOfSalesResults.get(i).getResult());

            xyzResultRepository.save(xyzResult);
        }
    }

    private List<XyzResultDto> executeXyzResultQuery(String sql, SqlParameterSource sqlParameterSource) {
        return namedParameterJdbcTemplate.query(
                sql,
                sqlParameterSource,
                new XyzRowMapper()
        );
    }
}
