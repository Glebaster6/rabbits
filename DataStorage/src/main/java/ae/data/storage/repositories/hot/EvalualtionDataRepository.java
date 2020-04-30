package ae.data.storage.repositories.hot;

import ae.data.storage.models.hot.EvaluationData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvalualtionDataRepository extends JpaRepository<EvaluationData, Long> {
}
