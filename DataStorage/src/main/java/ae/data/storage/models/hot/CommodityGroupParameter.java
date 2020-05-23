package ae.data.storage.models.hot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommodityGroupParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_group_id")
    private CommodityGroup commodityGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_group_characteristic_id")
    private CommodityGroupCharacteristic commodityGroupCharacteristic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_group_characteristic_value_id")
    private CommodityGroupCharacteristicValue commodityGroupCharacteristicValue;
}
