package ae.data.storage.models.hot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommodityGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @OneToMany(mappedBy = "commodityGroup")
    private List<EvaluationData> evaluationData;

    @OneToMany(mappedBy = "commodityGroup", fetch = FetchType.LAZY)
    private List<CommodityGroupParameter> commodityGroupParameters;

    private Instant created_at;

    private Instant updated_at;

    @PrePersist
    private void onCreate() {
        created_at = Instant.now();
    }

    @PreUpdate
    private void onUpdate() {
        Instant.now();
    }
}
