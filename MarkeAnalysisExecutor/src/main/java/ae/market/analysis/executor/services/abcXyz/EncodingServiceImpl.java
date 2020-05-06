package ae.market.analysis.executor.services.abcXyz;

import ae.market.analysis.executor.utils.AnalysisDecoder;
import main.dto.CommodityGroupResultDto;
import main.dto.MainDto;
import main.dto.MainUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncodingServiceImpl  implements EncodingService{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void encodeValuesOfCommodityGroup(CommodityGroupResultDto resultDto, String user) {

        String profitAbc = resultDto.getAbcProfitResult().toLowerCase();
        String revenueAbc = resultDto.getAbcRevenueResult().toLowerCase();
        String volumeOfSalesAbc = resultDto.getAbcVolumeOfSalesResult().toLowerCase();

        String xyzProfit = resultDto.getXyzProfitResult().toLowerCase();
        String xyzRevenue = resultDto.getXyzRevenueResult().toLowerCase();
        String xyzVolumeOfSales = resultDto.getXyzVolumeOfSalesResult().toLowerCase();

        resultDto.setAbcProfitResult(
                profitAbc.toUpperCase() + " - " + AnalysisDecoder.getAbcValue(profitAbc)
        );

        resultDto.setAbcRevenueResult(
                revenueAbc.toUpperCase() + " - " + AnalysisDecoder.getAbcValue(revenueAbc)
        );

        resultDto.setAbcVolumeOfSalesResult(
                volumeOfSalesAbc.toUpperCase() + " - " + AnalysisDecoder.getAbcValue(volumeOfSalesAbc)
        );

        resultDto.setXyzProfitResult(
                xyzProfit.toUpperCase() + " - " + AnalysisDecoder.getXyzValue(xyzProfit)
        );

        resultDto.setXyzRevenueResult(
                xyzRevenue.toUpperCase() + " - " + AnalysisDecoder.getXyzValue(xyzRevenue)
        );

        resultDto.setXyzVolumeOfSalesResult(
                xyzVolumeOfSales.toUpperCase() + " - " + AnalysisDecoder.getXyzValue(xyzVolumeOfSales)
        );

        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.RETURN_COMMODITY_GROUP_RESULT)
                .json(MainUtil.objectToJsonString(resultDto))
                .user(user)
                .build();

        rabbitTemplate.convertAndSend("marketAnalysisToAgregatorQueue", MainUtil.objectToByteArray(mainDto));

    }
}
