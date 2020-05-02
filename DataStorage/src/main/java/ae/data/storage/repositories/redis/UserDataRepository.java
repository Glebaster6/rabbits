package ae.data.storage.repositories.redis;

import ae.data.storage.models.redis.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserData, String> {
}
