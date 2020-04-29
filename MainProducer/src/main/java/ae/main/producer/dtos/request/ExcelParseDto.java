package ae.main.producer.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExcelParseDto {
    private Long facilityId;
    private String name;
    private String description;
    private String startFrom;
    private String lastNeeded;
}
