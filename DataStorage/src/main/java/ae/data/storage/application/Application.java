package ae.data.storage.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("ae.data.storage")
@EnableJpaRepositories(basePackages = "ae.data.storage.repositories.hot")
@EntityScan(basePackages = "ae.data.storage.models.hot")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
