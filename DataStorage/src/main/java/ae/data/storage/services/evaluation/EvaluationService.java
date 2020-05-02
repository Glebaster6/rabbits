package ae.data.storage.services.evaluation;

import main.dto.GetFacilityDataDto;
import main.dto.MainDto;

public interface EvaluationService {
    public void saveEvaluation(MainDto mainDto);
    public void saveEvaluationData(MainDto mainDto);
    public void getFacilityData(GetFacilityDataDto getFacilityDataDto);
}
