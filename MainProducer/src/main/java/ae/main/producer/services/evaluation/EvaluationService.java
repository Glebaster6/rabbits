package ae.main.producer.services.evaluation;

import main.dto.*;
import org.springframework.security.core.Authentication;

public interface EvaluationService {
    void createEvaluation(EvaluationCreateDto evaluationCreateDto, Authentication authentication);

    void getFacilityData(GetFacilityDataDto getFacilityDataDto, Authentication authentication);

    void deleteEvaluation(DeleteEvaluationDto deleteEvaluationDto);

    void getEvaluationDto(GetEvaluationDataDto getEvaluationDataDto, Authentication authentication);

    void getEvaluationDataByEvaluationAndPeriod(GetEvaluationDataByEvaluationAndPeriodDto dto, Authentication authentication);

    void getEvaluationResultById(GetCommodityGroupResultDto getDto, Authentication authentication);
}