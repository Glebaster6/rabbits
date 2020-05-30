package neural.net.analysis.executor.messaging.listeners;

import lombok.SneakyThrows;
import main.dto.MainDto;
import main.dto.MainUtil;
import neural.net.analysis.executor.services.prediction.PredictionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgregatorListener {
    @Autowired
    private PredictionService predictionService;

    @SneakyThrows
    @RabbitListener(queues = "agregatorToNeuralNetAnalysisQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

        try {
            switch (mainDto.getAction()){
                case GET_PREDICTION:{
                    predictionService.getModel(mainDto);
                    break;
                }
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
