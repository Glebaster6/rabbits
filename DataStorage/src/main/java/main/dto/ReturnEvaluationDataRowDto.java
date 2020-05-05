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
public class ReturnEvaluationDataRowDto implements Serializable {
    private Long id;
    private Long commodityGroupId;
    private String commodityGroupName;
    private Double revenue;
    private Double profit;
    private Double consumption;
    private Integer volumeOfSales;
}
