package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommodityGroupResultDto implements Serializable {
    private Long id;
    private String name;
    private Integer periodCount;
    private String period;
    private String startYear;
    private String startMonth;
    private String startDay;
    private String profitChart;
    private String revenueChart;
    private String consumptionChart;
    private String volumeOfSalesChart;
    private String profitPercentChart;
    private String revenuePercentChart;
    private String volumeOfSalesPercentChart;
    private String revenueResultPercentChart;
    private String profitResultPercentChart;
    private String volumeOfSalesResultPercentChart;
    private String profitResults;
    private String revenueResults;
    private String volumeOfSalesResults;
    private String abcProfitResult;
    private String abcRevenueResult;
    private String abcVolumeOfSalesResult;
    private String xyzProfitResult;
    private String xyzRevenueResult;
    private String xyzVolumeOfSalesResult;
    private String abcDecode;
    private String xyzDecode;
    private String abcXyzDecode;
}
