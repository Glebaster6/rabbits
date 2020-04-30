package ae.data.storage.services.evaluation;

import ae.data.storage.models.hot.Evaluation;
import ae.data.storage.models.hot.EvaluationData;
import ae.data.storage.models.hot.Facility;
import ae.data.storage.models.redis.DataMapper;
import ae.data.storage.repositories.hot.CommodityGroupRepository;
import ae.data.storage.repositories.hot.EvalualtionDataRepository;
import ae.data.storage.repositories.hot.EvaluationRepository;
import ae.data.storage.repositories.hot.FacilityRepository;
import ae.data.storage.repositories.redis.DataMapperRepository;
import main.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

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


    @Override
    public void saveEvaluation(MainDto mainDto) {
        EvaluationDto evaluationDataDto = (EvaluationDto) MainUtil.stringJsonToObject(
                mainDto.getJson(),
                EvaluationDataDto.class
        );

        Evaluation evaluation = evaluationRepository.save(Evaluation.builder()
                .facility(facilityRepository.findById(evaluationDataDto.getFacilityId()).get())
                .name(evaluationDataDto.getName())
                .startsFrom(LocalDate.parse(evaluationDataDto.getStartsFrom()).atStartOfDay(ZoneOffset.UTC).toInstant())
                .lastNeeded(Instant.now())
                .description(evaluationDataDto.getDescription())
                .build());

        dataMapperRepository.save(DataMapper.builder()
                .id(evaluationDataDto.getHash())
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

        Evaluation evaluation = evaluationRepository
                .findById(evaluationDataDto.getEvaluationDataRows().get(0).getEvaluationId())
                .get();
        Facility facility = evaluation.getFacility();


        for (EvaluationDataRowDto rowDto : evaluationDataDto.getEvaluationDataRows()) {
            evaluationData.add(EvaluationData.builder()
                    .commodityGroup(commodityGroupRepository.findByNameAndFacility(rowDto.getName(), facility).get())
                    .build()
            );
        }

        evalualtionDataRepository.saveAll(evaluationData);
    }
}
