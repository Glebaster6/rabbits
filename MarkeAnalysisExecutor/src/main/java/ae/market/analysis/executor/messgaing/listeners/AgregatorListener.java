package ae.market.analysis.executor.messgaing.listeners;

import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgregatorListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "mainProducerToAgregatorQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);
        switch (mainDto.getAction()){
            case GET_COMMODITY_GROUP_RESULT:{
                rabbitTemplate.convertAndSend("marketAnalysisToDataStorageQueue", MainUtil.objectToByteArray(mainDto));
                break;
            }
        }
    }
}
