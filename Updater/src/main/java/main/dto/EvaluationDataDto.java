package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDataDto implements Serializable {
    private List<EvaluationDataRowDto> evaluationDataRows;
    private String hash;
    private Boolean isLast;
}
