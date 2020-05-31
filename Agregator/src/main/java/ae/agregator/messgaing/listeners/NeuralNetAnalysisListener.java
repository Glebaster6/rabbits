package ae.agregator.messgaing.listeners;

import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NeuralNetAnalysisListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "neuralNetAnalysisToAgregatorQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);
        switch (mainDto.getAction()){
            case RETURN_PREDICTION:{
                rabbitTemplate.convertAndSend("agregatorToMainProducerQueue", MainUtil.objectToByteArray(mainDto));
                break;
            }
        }
    }
}
