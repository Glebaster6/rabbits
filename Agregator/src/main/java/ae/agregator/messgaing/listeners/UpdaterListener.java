package ae.agregator.messgaing.listeners;

import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdaterListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "updaterToAgregatorQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

        switch (mainDto.getAction()) {
            case RETURN_EVALUATION_DATA:
            case RETURN_EVALUATION_DATA_BY_EVALUATION_AND_PERIOD:
            case RETURN_FACILITY_DATA: {
                rabbitTemplate.convertAndSend("agregatorToMainProducerQueue", MainUtil.objectToByteArray(mainDto));
                break;
            }
        }
    }
}
