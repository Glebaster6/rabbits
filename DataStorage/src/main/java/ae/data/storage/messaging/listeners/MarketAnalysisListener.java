package ae.data.storage.messaging.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MarketAnalysisListener {
    @RabbitListener(queues = "marketAnalysisToDataStorageQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
