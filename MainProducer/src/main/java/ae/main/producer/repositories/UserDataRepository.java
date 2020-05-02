package ae.main.producer.repositories;

import ae.main.producer.models.UserData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDataRepository extends CrudRepository<UserData, String> {
    Optional<UserData> findByIdAndPassword(String id, String password);
}
