package ae.data.storage.repositories.hot;

import ae.data.storage.models.hot.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
