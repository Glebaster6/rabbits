package ae.market.analysis.executor.messgaing;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Queues {

    @Bean
    public Queue marketAnalysisToDataStorageQueue(){
        return new Queue("marketAnalysisToDataStorageQueue", false);
    }

    @Bean
    public Queue marketAnalysisToAgregatorQueue(){
        return new Queue("marketAnalysisToAgregatorQueue", false);
    }

    @Bean
    public Queue dataStorageToMarketAnalysisQueue(){
        return new Queue("dataStorageToMarketAnalysisQueue", false);
    }

    @Bean
    public Queue agregatorToMarketAnalysisQueue(){
        return new Queue("agregatorToMarketAnalysisQueue", false);
    }
}
