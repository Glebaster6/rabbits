package ae.data.storage.repositories.cold;

import ae.data.storage.models.cold.AnalysisData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnalysisDataRepository extends CassandraRepository<AnalysisData, UUID> {
}
