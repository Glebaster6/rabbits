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
public class AbcResultDto implements Serializable {
    private Long commodityGroupId;
    private String profitResult;
    private Float profitPercent;
    private Float profitResultPercent;
    private String revenueResult;
    private Float revenuePercent;
    private Float revenueResultPercent;
    private String volumeOfSalesResult;
    private Float volumeOfSalesPercent;
    private Float volumeOfSalesResultPercent;
}
