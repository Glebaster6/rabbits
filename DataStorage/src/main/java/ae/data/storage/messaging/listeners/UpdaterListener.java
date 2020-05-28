package ae.data.storage.messaging.listeners;

import ae.data.storage.services.evaluation.EvaluationService;
import ae.data.storage.services.user.UserService;
import main.dto.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdaterListener {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private UserService userService;

    @RabbitListener(queues = "updaterToDataStorageQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

        System.out.println(mainDto.getAction());
        try {
            switch (mainDto.getAction()) {
                case SAVE_EVALUATION: {
                    evaluationService.saveEvaluation(mainDto);
                    break;
                }
                case SAVE_EVALUATION_DATA: {
                    evaluationService.saveEvaluationData(mainDto);
                    break;
                }
                case LOGIN: {
                    userService.login((LoginDto)
                            MainUtil.stringJsonToObject(
                                    mainDto.getJson(),
                                    LoginDto.class)
                    );
                    break;
                }
                case REGISTER: {
                    userService.register(
                            (RegistrationDto) MainUtil.stringJsonToObject(
                                    mainDto.getJson(),
                                    RegistrationDto.class
                            )
                    );
                    break;
                }
                case GET_FACILITY_DATA: {
                    evaluationService.getFacilityData(
                            (GetFacilityDataDto) MainUtil.stringJsonToObject(
                                    mainDto.getJson(),
                                    GetFacilityDataDto.class
                            ),
                            mainDto.getUser()
                    );
                    break;
                }
                case DELETE_EVALUATION: {
                    evaluationService.deleteEvaluation(
                            (DeleteEvaluationDto) MainUtil.stringJsonToObject(
                                    mainDto.getJson(),
                                    DeleteEvaluationDto.class
                            )
                    );
                    break;
                }
                case GET_EVALUATION_DATA:{
                    evaluationService.getEvaluationData(
                            (GetEvaluationDataDto) MainUtil.stringJsonToObject(
                                    mainDto.getJson(),
                                    GetEvaluationDataDto.class
                            ),
                            mainDto.getUser()
                    );
                    break;
                }
                case GET_EVALUATION_DATA_BY_EVALUATION_AND_PERIOD:{
                    evaluationService.getEvaluationDataByEvaluationAndPeriod(
                            (GetEvaluationDataByEvaluationAndPeriodDto) MainUtil.stringJsonToObject(
                                    mainDto.getJson(),
                                    GetEvaluationDataByEvaluationAndPeriodDto.class
                            ),
                            mainDto.getUser()
                    );
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
