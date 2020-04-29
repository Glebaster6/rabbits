package ae.data.storage.models.cold;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalysisData {

    @PrimaryKeyColumn(
           name = "id",
            ordinal = 2,
            ordering = Ordering.DESCENDING,
            type = PrimaryKeyType.PARTITIONED

    )
    private UUID id;

    @Column
    private Long facilityId;

    @Column
    private Long evaluationId;

    @Column
    private Long commodityGroupId;

    @Column
    private Instant startsFrom;

    @Column
    private Integer period;

    @Column
    private Float revenue;

    @Column
    private Float consumption;

    @Column
    private Long volumeOfSales;
}
