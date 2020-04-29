package ae.updater.repositories;

import ae.updater.models.DataMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataMapperRepository extends CrudRepository<DataMapper, String> {
}
