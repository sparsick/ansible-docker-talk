package db.migration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 *
 * @author sparsick
 */
@Testcontainers
class DbMigrationITest {

    @Container
    private MySQLContainer mysqlDb = new MySQLContainer();

    @Test
    void testDbMigrationFromTheScratch() {
        FluentConfiguration dbConfig = Flyway.configure()
                .dataSource(mysqlDb.getJdbcUrl(), mysqlDb.getUsername(), mysqlDb.getPassword());
        Flyway flyway = new Flyway(dbConfig);
        flyway.migrate();

        flyway.migrate();
    }

}
