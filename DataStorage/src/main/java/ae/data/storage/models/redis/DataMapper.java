package ae.data.storage.models.redis;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("DataMapper")
public class DataMapper {
    private String id;
    private Long dataId;
}

