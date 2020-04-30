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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "facility", fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(mappedBy = "facility", fetch = FetchType.LAZY)
    private List<File> files;

    @OneToMany(mappedBy = "facility", fetch = FetchType.LAZY)
    private List<CommodityGroup> commodityGroups;

    @OneToMany(mappedBy = "facility", fetch = FetchType.LAZY)
    private List<CommodityGroupCharacteristic> commodityGroupCharacteristics;

    @OneToMany(mappedBy = "facility", fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;

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
