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
public class ReturnEvaluationDataRow implements Serializable {
    private String period;
    private Long cgCount;
    private Integer periodNum;
}
