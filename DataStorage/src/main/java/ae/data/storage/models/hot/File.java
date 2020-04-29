package ae.data.storage.models.hot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String storingName;
    private String path;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

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
