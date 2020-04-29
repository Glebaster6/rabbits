package ae.updater.dtos.data.storage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvaluationDto {
    private Long id;
    private Long facilityId;
    private String name;
    private String description;
    private String startsFrom;
    private String lastNeeded;
}
