package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDataRowDto {
    private Long id;
    private Long evaluationId;
    private String name;
    private Integer period;
    private Double revenue;
    private Double consumption;
    private Integer volumeOfSales;
}
