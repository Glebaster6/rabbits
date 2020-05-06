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
public class GetEvaluationDataByEvaluationAndPeriodDto implements Serializable {
    private Long evaluationId;
    private Integer period;
}