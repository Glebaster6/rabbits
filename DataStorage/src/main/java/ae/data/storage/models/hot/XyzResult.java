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
public class XyzResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profitResult;
    private Float profitPercent;

    private String revenueResult;
    private Float revenuePercent;

    private String volumeOfSalesResult;
    private Float volumeOfSalesPercent;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluation_data_id", referencedColumnName = "id")
    private EvaluationData evaluationData;
}
