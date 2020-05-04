package ae.main.producer.messaging.listeners;

import lombok.SneakyThrows;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AgregatorListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @SneakyThrows
    @RabbitListener(queues = "agregatorToMainProducerQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

        Thread.sleep(400);
        simpMessagingTemplate.convertAndSendToUser(mainDto.getUser(), "/queue/notify", MainUtil.objectToJsonString(mainDto));

    }

}
