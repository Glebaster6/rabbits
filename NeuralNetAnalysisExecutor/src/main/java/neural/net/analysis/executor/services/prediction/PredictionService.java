package neural.net.analysis.executor.services.prediction;

import main.dto.MainDto;

public interface PredictionService {
    void makePrediction(MainDto mainDto);
    void getModel(MainDto mainDto);
}
