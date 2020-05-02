package ae.main.producer.services.evaluation;

import main.dto.EvaluationCreateDto;
import main.dto.EvaluationDto;
import main.dto.GetFacilityDataDto;

public interface EvaluationService {
    void createEvaluation(EvaluationCreateDto evaluationCreateDto);

    void getFacilityData(GetFacilityDataDto getFacilityDataDto);
}
