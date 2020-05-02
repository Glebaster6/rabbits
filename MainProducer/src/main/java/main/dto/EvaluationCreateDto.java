package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationCreateDto implements Serializable {
    private Long id;
    private Long facilityId;
    private String name;
    private String description;
    private String startsFrom;
    private String lastNeeded;
    private String hash;
    private File file;
}