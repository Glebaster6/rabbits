package ae.updater.messaging.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FileStorageListener {
    @RabbitListener(queues = "fileStorageToUpdaterQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
