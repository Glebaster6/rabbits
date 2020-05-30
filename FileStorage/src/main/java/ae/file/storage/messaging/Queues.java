package ae.file.storage.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Queues {
    @Bean
    public Queue dataStorageToMarketAnalysisQueue(){
        return new Queue("fileStorageToUpdaterQueue", false);
    }

    @Bean
    public Queue dataStorageToUpdaterQueue(){
        return new Queue("fileStorageToNeuralNetAnalysisQueue", false);
    }

    @Bean
    public Queue marketAnalysisToDataStorageQueue(){
        return new Queue("neuralNetAnalysisToFileStorageQueue", false);
    }

    @Bean
    public Queue updaterToDataStorageQueue(){
        return new Queue("updaterToFileStorageQueue", false);
    }
}
