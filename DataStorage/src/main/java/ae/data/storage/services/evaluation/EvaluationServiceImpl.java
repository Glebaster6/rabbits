package ae.data.storage.services.evaluation;

import ae.data.storage.models.cold.AnalysisData;
import ae.data.storage.models.hot.CommodityGroup;
import ae.data.storage.models.hot.Evaluation;
import ae.data.storage.models.hot.EvaluationData;
import ae.data.storage.models.hot.Facility;
import ae.data.storage.models.redis.DataMapper;
import ae.data.storage.repositories.cold.AnalysisDataRepository;
import ae.data.storage.repositories.hot.CommodityGroupRepository;
import ae.data.storage.repositories.hot.EvalualtionDataRepository;
import ae.data.storage.repositories.hot.EvaluationRepository;
import ae.data.storage.repositories.hot.FacilityRepository;
import ae.data.storage.repositories.redis.DataMapperRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import main.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        List<EvaluationData> evaluationData = new ArrayList<>();
        List<AnalysisData> analysisDataList = new ArrayList<>();

        DataMapper data = dataMapperRepository.findById(hash).get();

        Evaluation evaluation = evaluationRepository
                .findById(data.getDataId())
                .get();

        Facility facility = evaluation.getFacility();

        for (EvaluationDataRowDto rowDto : evaluationDataDto.getEvaluationDataRows()) {
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
                    .volumeOfSales((long)rowDto.getVolumeOfSales())
                    .evaluationName(evaluation.getName())
                    .evaluationDescription(evaluation.getDescription())
                    .build()
            );
        }

        analysisDataRepository.saveAll(analysisDataList);
        evalualtionDataRepository.saveAll(evaluationData);

        if (evaluationDataDto.getIsLast()){
            dataMapperRepository.delete(data);
        }
    }

    @Override
    public void getFacilityData(GetFacilityDataDto getFacilityDataDto) {
        Facility facility = facilityRepository.findById(getFacilityDataDto.getFacilityId()).get();
    }
}
