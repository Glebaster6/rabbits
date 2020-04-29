package ae.main.producer.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Queues {
    @Bean
    public Queue mainProducerToAgregatorQueue(){
        return new Queue("mainProducerToAgregatorQueue", false);
    }

    @Bean
    public Queue agregatorToMainProducerQueue(){
        return new Queue("agregatorToMainProducerQueue", false);
    }
}
