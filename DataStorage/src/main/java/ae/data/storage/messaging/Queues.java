package ae.data.storage.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Queues {
    @Bean
    public Queue dataStorageToMarketAnalysisQueue(){
        return new Queue("dataStorageToMarketAnalysisQueue", false);
    }

    @Bean
    public Queue dataStorageToUpdaterQueue(){
        return new Queue("dataStorageToUpdaterQueue", false);
    }

    @Bean
    public Queue marketAnalysisToDataStorageQueue(){
        return new Queue("marketAnalysisToDataStorageQueue", false);
    }

    @Bean
    public Queue updaterToDataStorageQueue(){
        return new Queue("updaterToDataStorageQueue", false);
    }
}
