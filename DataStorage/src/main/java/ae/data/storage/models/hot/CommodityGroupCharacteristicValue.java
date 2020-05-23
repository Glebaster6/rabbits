package ae.data.storage.models.hot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityGroupCharacteristicValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private Integer numberValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_group_characteristic_id")
    private CommodityGroupCharacteristic commodityGroupCharacteristic;

    @OneToMany(mappedBy = "commodityGroupCharacteristicValue", fetch = FetchType.LAZY)
    private List<CommodityGroupParameter> commodityGroupParameters;

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
