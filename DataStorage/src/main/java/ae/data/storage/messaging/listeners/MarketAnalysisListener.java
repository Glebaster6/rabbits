package ae.data.storage.messaging.listeners;

import ae.data.storage.services.evaluation.EvaluationService;
import main.dto.GetCommodityGroupResultDto;
import main.dto.GetFacilityDataDto;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketAnalysisListener {

    @Autowired
    private EvaluationService evaluationService;

    @RabbitListener(queues = "marketAnalysisToDataStorageQueue")
    public void listen(byte[] in) {
        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);

        try {
            switch (mainDto.getAction()) {
                case GET_COMMODITY_GROUP_RESULT: {
                    evaluationService.getCommodityGroupResult(
                            (GetCommodityGroupResultDto) MainUtil.stringJsonToObject(
                                    mainDto.getJson(),
                                    GetCommodityGroupResultDto.class
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

