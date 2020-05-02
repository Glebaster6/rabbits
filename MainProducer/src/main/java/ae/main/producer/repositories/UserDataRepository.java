package ae.main.producer.repositories;

import ae.main.producer.models.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserData, String> {
}
