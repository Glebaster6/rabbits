package ae.agregator.repositories;

import ae.agregator.models.DataMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataMapperRepository extends CrudRepository<DataMapper, String> {
}
