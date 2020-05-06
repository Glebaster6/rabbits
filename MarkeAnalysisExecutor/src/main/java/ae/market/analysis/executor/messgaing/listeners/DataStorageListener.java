package ae.market.analysis.executor.messgaing.listeners;

import ae.market.analysis.executor.services.abcXyz.EncodingService;
import main.dto.CommodityGroupResultDto;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataStorageListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EncodingService encodingService;

    @RabbitListener(queues = "dataStorageToMarketAnalysisQueue")
    public void listen(byte[] in) {
//        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);
//        switch (mainDto.getAction()){
//            case RETURN_COMMODITY_GROUP_RESULT:{
//                rabbitTemplate.convertAndSend("marketAnalysisToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
//                break;
//            }
//        }

        MainDto mainDto = (MainDto) MainUtil.byteArrayToObject(in);
        switch (mainDto.getAction()){
            case RETURN_COMMODITY_GROUP_RESULT:{
                encodingService.encodeValuesOfCommodityGroup(
                        (CommodityGroupResultDto) MainUtil.stringJsonToObject(
                                mainDto.getJson(),
                                CommodityGroupResultDto.class
                        ), mainDto.getUser()
                );
                break;
            }
        }
    }
}
