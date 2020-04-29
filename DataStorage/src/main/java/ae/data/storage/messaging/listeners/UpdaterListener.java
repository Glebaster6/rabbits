package ae.data.storage.messaging.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UpdaterListener {
    @RabbitListener(queues = "updaterToDataStorageQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
