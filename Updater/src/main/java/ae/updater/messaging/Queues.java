package ae.updater.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Queues {

    @Bean
    public Queue updaterToDataStorageQueue(){
        return new Queue("updaterToDataStorageQueue", false);
    }

    @Bean
    public Queue updaterToFileStorageQueue(){
        return new Queue("updaterToFileStorageQueue", false);
    }

    @Bean
    public Queue updaterToAgregatorQueue(){
        return new Queue("updaterToDataStorageQueue", false);
    }

    @Bean
    public Queue dataStorageToUpdaterQueue(){
        return new Queue("dataStorageToUpdaterQueue", false);
    }

    @Bean
    public Queue fileStorageToUpdaterQueue(){
        return new Queue("fileStorageToUpdaterQueue", false);
    }

    @Bean
    public Queue agregatorToUpdaterQueue(){
        return new Queue("agregatorToUpdaterQueue", false);
    }
}
