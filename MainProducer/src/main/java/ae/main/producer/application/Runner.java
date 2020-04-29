package ae.main.producer.application;


import ae.main.producer.dtos.request.ExcelParseDto;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {
        File file = new File("src/main/resources/commodity_groups.xlsx");
        MainDto mainDto = MainDto.builder()
                .file(IOUtils.toByteArray(new FileInputStream(file)))
                .action(MainDto.Action.PARSE_EXCEL)
                .json(MainUtil.objectToJsonString(ExcelParseDto.builder()
                        .name("test")
                        .description("for test")
                        .startFrom("2018-02-01")
                        .facilityId(1L)
                        .build()))
                .build();

        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }
}
