package ae.main.producer.messaging.listeners;

import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AgregatorListener {

    @RabbitListener(queues = "agregatorToMainProducerQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);
        System.out.println();
    }

}
