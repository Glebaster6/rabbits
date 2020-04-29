package ae.updater.application;

import ae.updater.utils.ExcelDataReader;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private ExcelDataReader excelDataReader;

    @Override
    public void run(String... args) throws Exception {
//        File file = new File("src/main/resources/commodity_groups.xlsx");
//        excelDataReader.readData(file);
//        rabbitTemplate.convertAndSend("dataStorageToMarketAnalysisQueue", "Hello, world!");
    }
}
