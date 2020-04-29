package ae.agregator.messgaing;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Queues {
    @Bean
    public Queue  agregatorToUpdaterQueue(){
        return new Queue("agregatorToUpdaterQueue", false);
    }

    @Bean
    public Queue agregatorToMarketAnalysisQueue(){
        return new Queue("agregatorToMarketAnalysisQueue", false);
    }

    @Bean
    public Queue agregatorToNeuralNetAnalysisQueue(){
        return new Queue("agregatorToNeuralNetAnalysisQueue", false);
    }

    @Bean
    public Queue agregatorToMainProducerQueue(){
        return new Queue("agregatorToMainProducerQueue", false);
    }

    @Bean
    public Queue  updaterToAgregatorQueue(){
        return new Queue("updaterToAgregatorQueue", false);
    }

    @Bean
    public Queue marketAnalysisToAgregatorQueue(){
        return new Queue("marketAnalysisToAgregatorQueue", false);
    }

    @Bean
    public Queue neuralNetAnalysisToAgregatorQueue(){
        return new Queue("neuralNetAnalysisToAgregatorQueue", false);
    }

    @Bean
    public Queue mainProducerToAgregatorQueue(){
        return new Queue("mainProducerToAgregatorQueue", false);
    }
}
