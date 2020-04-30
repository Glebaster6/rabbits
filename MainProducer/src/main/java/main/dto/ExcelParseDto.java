package main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExcelParseDto {
    private String hash;w
    private Long facilityId;
    private String name;
    private String description;
    private String startFrom;
    private String lastNeeded;
}
