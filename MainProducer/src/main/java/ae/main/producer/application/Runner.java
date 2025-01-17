package ae.main.producer.application;

import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @SneakyThrows
    @Override
    public void run(String... args) throws Exception {
//        File file = new File("src/main/resources/commodity_groups.xlsx");
//        String hash = RandomStringUtils.randomAscii(15);
//
//        MainDto mainDto = MainDto.builder()
//                .file(IOUtils.toByteArray(new FileInputStream(file)))
//                .action(MainDto.Action.PARSE_EXCEL)
//                .json(MainUtil.objectToJsonString(EvaluationDto.builder()
//                        .name("test")
//                        .description("for test")
//                        .startsFrom("2018-02-01")
//                        .facilityId(1L)
//                        .hash(hash)
//                        .lastNeeded("2018-02-01")
//                        .id(1L)
//                        .build()))
//                .build();
//
//        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));



    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
