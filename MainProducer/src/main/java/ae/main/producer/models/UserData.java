package ae.main.producer.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@Data
@Builder
@RedisHash("UserData")
public class UserData {
    private String id;
    private String password;
    private Boolean isCorrect;
    private String role;
    private Instant lastUsed;
}