package ae.data.storage.repositories.redis;

import ae.data.storage.models.redis.DataMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataMapperRepository extends CrudRepository<DataMapper, String> {
}
