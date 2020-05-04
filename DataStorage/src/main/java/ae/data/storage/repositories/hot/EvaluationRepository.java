package ae.data.storage.repositories.hot;

import ae.data.storage.models.hot.Evaluation;
import ae.data.storage.models.hot.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByFacility(Facility facility);
}
