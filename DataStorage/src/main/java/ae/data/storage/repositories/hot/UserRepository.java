package ae.data.storage.repositories.hot;

import ae.data.storage.models.hot.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
