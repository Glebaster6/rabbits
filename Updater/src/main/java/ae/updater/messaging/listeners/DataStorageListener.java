package ae.updater.messaging.listeners;

import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataStorageListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "dataStorageToUpdaterQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

        switch (mainDto.getAction()){
            case RETURN_FACILITY_DATA: {
                rabbitTemplate.convertAndSend("updaterToDataStorageQueue", MainUtil.objectToByteArray(mainDto));
                break;
            }
        }
    }
}
