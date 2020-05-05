package ae.data.storage.services.evaluation;

import ae.data.storage.models.hot.Evaluation;
import main.dto.*;

import java.util.List;

public interface EvaluationService {
    void saveEvaluation(MainDto mainDto);

    void saveEvaluationData(MainDto mainDto);

    void getFacilityData(GetFacilityDataDto getFacilityDataDto, String username);

    void deleteEvaluation(DeleteEvaluationDto deleteEvaluationDto);

    void getEvaluationData(GetEvaluationDataDto getEvaluationDataDto, String username);

    public void getCommodityGroupResult(GetCommodityGroupResultDto getCommodityGroupResultDto, String user);

    public void getEvaluationDataByEvaluationAndPeriod(GetEvaluationDataByEvaluationAndPeriodDto dto, String username);
}
