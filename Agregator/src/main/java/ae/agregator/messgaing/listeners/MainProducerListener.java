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
            case PARSE_EXCEL:{
                rabbitTemplate.convertAndSend("agregatorToUpdaterQueue", MainUtil.objectToByteArray(mainDto));
                break;
            }
        }
    }
}
