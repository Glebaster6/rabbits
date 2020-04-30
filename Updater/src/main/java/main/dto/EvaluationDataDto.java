package main.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EvaluationDataDto {
    private List<EvaluationDataRowDto> evaluationDataRows;
    private String hash;
    private Boolean isLast;
}
