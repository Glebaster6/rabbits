package ae.main.producer.services.evaluation;

import ae.main.producer.models.UserData;
import ae.main.producer.services.authentication.AuthenticationService;
import lombok.SneakyThrows;
import main.dto.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    @SneakyThrows
    public void createEvaluation(EvaluationCreateDto evaluationCreateDto, Authentication authentication) {
        String hash = RandomStringUtils.randomAscii(15);
        UserData userData = authenticationService.getUserByAuthentication(authentication);

        MainDto mainDto = MainDto.builder()
                .file(evaluationCreateDto.getFile().getBytes())
                .action(MainDto.Action.PARSE_EXCEL)
                .json(MainUtil.objectToJsonString(EvaluationDto.builder()
                        .name(evaluationCreateDto.getName())
                        .description(evaluationCreateDto.getDescription())
                        .startsFrom(evaluationCreateDto.getStartsFrom())
                        .facilityId(userData.getFacilityId())
                        .hash(hash)
                        .lastNeeded(Instant.now()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .format(DateTimeFormatter.ISO_LOCAL_DATE))
                        .build()))
                .build();

        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }

    @Override
    public void getFacilityData(GetFacilityDataDto getFacilityDataDto,
                                Authentication authentication) {
        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.GET_FACILITY_DATA)
                .json(MainUtil.objectToJsonString(getFacilityDataDto))
                .user(authenticationService.getUserByAuthentication(authentication).getId())
                .build();

        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }

    @Override
    public void deleteEvaluation(DeleteEvaluationDto deleteEvaluationDto) {
        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.DELETE_EVALUATION)
                .json(MainUtil.objectToJsonString(deleteEvaluationDto))
                .build();
        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }

    @Override
    public void getEvaluationDto(GetEvaluationDataDto getEvaluationDataDto,
                                 Authentication authentication){
        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.GET_EVALUATION_DATA)
                .json(MainUtil.objectToJsonString(getEvaluationDataDto))
                .user(authenticationService.getUserByAuthentication(authentication).getId())
                .build();
        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }
}
