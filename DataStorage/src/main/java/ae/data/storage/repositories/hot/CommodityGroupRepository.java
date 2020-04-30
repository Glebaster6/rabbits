package ae.data.storage.repositories.hot;

import ae.data.storage.models.hot.CommodityGroup;
import ae.data.storage.models.hot.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommodityGroupRepository extends JpaRepository<CommodityGroup, Long> {
    Optional<CommodityGroup> findByNameAndFacility(String name, Facility facility);
}
