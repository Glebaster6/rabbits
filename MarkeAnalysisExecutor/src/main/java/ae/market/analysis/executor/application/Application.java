package ae.market.analysis.executor.application;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ae.market.analysis.executor")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
