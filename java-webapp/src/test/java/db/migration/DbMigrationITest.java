package db.migration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

/**
 *
 * @author sparsick
 */
public class DbMigrationITest {

    @Rule
    public MySQLContainer mysqlDb = new MySQLContainer();

    @Test
    public void testDbMigrationFromTheScratch() {
        FluentConfiguration dbConfig = Flyway.configure()
                .dataSource(mysqlDb.getJdbcUrl(), mysqlDb.getUsername(), mysqlDb.getPassword());
        Flyway flyway = new Flyway(dbConfig);
        flyway.migrate();

        flyway.migrate();
    }

}
