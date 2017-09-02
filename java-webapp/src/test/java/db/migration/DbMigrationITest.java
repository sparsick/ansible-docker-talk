package db.migration;

import org.flywaydb.core.Flyway;
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
    public void testDbMigrationFromTheScratch(){
        Flyway flyway = new Flyway();
        flyway.setDataSource(mysqlDb.getJdbcUrl(), mysqlDb.getUsername(), mysqlDb.getPassword());
        
        flyway.migrate();
    }
    
}