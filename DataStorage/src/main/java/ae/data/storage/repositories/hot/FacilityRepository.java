package ae.data.storage.repositories.hot;

import ae.data.storage.models.hot.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
