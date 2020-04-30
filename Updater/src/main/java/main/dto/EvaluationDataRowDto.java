package main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvaluationDataRowDto {
    private Long id;
    private Long evaluationId;
    private String name;
    private Integer period;
    private Double revenue;
    private Double consumption;
    private Integer volumeOfSales;

}
