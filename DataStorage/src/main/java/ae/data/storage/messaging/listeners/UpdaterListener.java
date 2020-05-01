package ae.data.storage.messaging.listeners;

import ae.data.storage.services.evaluation.EvaluationService;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdaterListener {

    @Autowired
    private EvaluationService evaluationService;

    @RabbitListener(queues = "updaterToDataStorageQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

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
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
