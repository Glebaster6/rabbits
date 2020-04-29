package ae.market.analysis.executor.messgaing.from;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FromDataStorage {
    @RabbitListener(queues = "dataStorageToMarketAnalysisQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
