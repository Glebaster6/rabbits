package ae.market.analysis.executor.messgaing.from;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FromAgregator {
    @RabbitListener(queues = "agregatorToMarketAnalysisQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
