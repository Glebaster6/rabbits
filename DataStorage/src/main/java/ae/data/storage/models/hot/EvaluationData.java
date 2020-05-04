package ae.data.storage.models.hot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer period;

    private Double consumption;

    private Double revenue;

    private Integer volumeOfSales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_group_id")
    private CommodityGroup commodityGroup;

    @OneToOne(mappedBy = "evaluationData", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private AbcResult abcResult;

    @OneToOne(mappedBy = "evaluationData", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private XyzResult xyzResult;

    private Instant created_at;

    private Instant updated_at;

    @PrePersist
    private void onCreate() {
        created_at = Instant.now();
    }

    @PreUpdate
    private void onUpdate() {
        updated_at = Instant.now();
    }
}
