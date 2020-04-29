package ae.data.storage.confIg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "ae.data.storage.repositories.cold")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.host}")
    private String host;

    @Value("${cassandra.port}")
    private Integer port;

    @Value("${cassandra.username}")
    private String username;

    @Value("${cassandra.password}")
    private String password;



    protected String getKeyspaceName() {
        return keyspace;
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(host);
        cluster.setPort(port);
        cluster.setUsername(username);
        cluster.setPassword(password);
        cluster.setKeyspaceCreations(getKeyspaceCreations());
        return cluster;
    }

    @Bean
    public CassandraMappingContext cassandraMapping()
            throws ClassNotFoundException {
        return new CassandraMappingContext();
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        List<CreateKeyspaceSpecification> list = new ArrayList<>();
        list.add(specification);
        return list;
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        List<DropKeyspaceSpecification> list = new ArrayList<>();
        list.add(DropKeyspaceSpecification.dropKeyspace(keyspace));
        return list;
    }
}
