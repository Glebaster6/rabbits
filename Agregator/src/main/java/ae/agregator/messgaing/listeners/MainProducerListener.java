package ae.agregator.messgaing.listeners;

import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainProducerListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "mainProducerToAgregatorQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);
        switch (mainDto.getAction()){
            case LOGIN:
            case REGISTER:
            case GET_FACILITY_DATA:
            case DELETE_EVALUATION:
            case GET_EVALUATION_DATA:
            case GET_EVALUATION_DATA_BY_EVALUATION_AND_PERIOD:
            case PARSE_EXCEL:{
                rabbitTemplate.convertAndSend("agregatorToUpdaterQueue", MainUtil.objectToByteArray(mainDto));
                break;
            }
            case GET_COMMODITY_GROUP_RESULT:{
                rabbitTemplate.convertAndSend("agregatorToMarketAnalysisQueue", MainUtil.objectToByteArray(mainDto));
                break;
            }
        }
    }
}
