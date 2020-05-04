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
public class AbcResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String revenueResult;
    private Float revenuePercent;
    private Float revenueResultPercent;

    private String profitResult;
    private Float profitPercent;
    private Float profitResultPercent;

    private String volumeOfSalesResult;
    private Float volumeOfSalesPercent;
    private Float volumeOfSalesResultPercent;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluation_data_id", referencedColumnName = "id")
    private EvaluationData evaluationData;
}
