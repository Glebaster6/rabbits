package ae.main.producer.services.evaluation;

import lombok.SneakyThrows;
import main.dto.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @SneakyThrows
    public void createEvaluation(EvaluationCreateDto evaluationCreateDto) {
        String hash = RandomStringUtils.randomAscii(15);

        MainDto mainDto = MainDto.builder()
                .file(IOUtils.toByteArray(new FileInputStream(evaluationCreateDto.getFile())))
                .action(MainDto.Action.PARSE_EXCEL)
                .json(MainUtil.objectToJsonString(EvaluationDto.builder()
                        .name(evaluationCreateDto.getName())
                        .description(evaluationCreateDto.getDescription())
                        .startsFrom(evaluationCreateDto.getStartsFrom())
                        .facilityId(evaluationCreateDto.getFacilityId())
                        .hash(hash)
                        .lastNeeded(Instant.now()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .format(DateTimeFormatter.ISO_LOCAL_DATE))
                        .build()))
                .build();

        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }

    public void getFacilityData(GetFacilityDataDto getFacilityDataDto) {
        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.GET_FACILITY_DATA)
                .json(MainUtil.objectToJsonString(getFacilityDataDto))
                .build();

        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }
}
