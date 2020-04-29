package ae.updater.messaging.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DataStorageListener {
    @RabbitListener(queues = "dataStorageToUpdaterQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
