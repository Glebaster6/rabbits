package ae.main.producer.application;


import main.dto.MainDto;
import main.dto.MainUtil;
import main.dto.EvaluationDto;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
        String hash = RandomStringUtils.randomAscii(15);

        MainDto mainDto = MainDto.builder()
                .file(IOUtils.toByteArray(new FileInputStream(file)))
                .action(MainDto.Action.PARSE_EXCEL)
                .json(MainUtil.objectToJsonString(EvaluationDto.builder()
                        .name("test")
                        .description("for test")
                        .startsFrom("2018-02-01")
                        .facilityId(1L)
                        .hash(hash)
                        .lastNeeded("2018-02-01")
                        .id(1L)
                        .build()))
                .build();

        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }
}
