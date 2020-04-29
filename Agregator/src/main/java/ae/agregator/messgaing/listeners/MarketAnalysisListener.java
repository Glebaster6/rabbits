package ae.agregator.messgaing.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MarketAnalysisListener {
    @RabbitListener(queues = "marketAnalysisToAgregatorQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
