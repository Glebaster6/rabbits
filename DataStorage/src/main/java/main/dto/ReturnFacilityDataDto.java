package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnFacilityDataDto {
    private Long facilityId;
    private String facilityName;
    private String facilityDescription;
    private List<EvaluationRowDataDto> evaluations;
}
