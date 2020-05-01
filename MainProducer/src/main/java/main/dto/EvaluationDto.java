package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDto {
    private Long id;
    private Long facilityId;
    private String name;
    private String description;
    private String startsFrom;
    private String lastNeeded;
    private String hash;
}
