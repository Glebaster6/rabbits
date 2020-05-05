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
public class ReturnEvaluationData implements Serializable {
    private Long evaluationId;
    private Long facilityId;
    private String evaluationName;
    private String evaluationDescription;
    private List<ReturnEvaluationDataRow> evaluations;
}
