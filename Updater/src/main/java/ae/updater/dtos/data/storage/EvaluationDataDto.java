package ae.updater.dtos.data.storage;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EvaluationDataDto {
    List<EvaluationDataRowDto> evaluationDataRows;
}
