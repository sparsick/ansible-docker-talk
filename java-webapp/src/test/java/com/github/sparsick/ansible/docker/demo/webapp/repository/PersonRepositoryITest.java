package com.github.sparsick.ansible.docker.demo.webapp.repository;

import static org.junit.Assert.assertThat;

import com.github.sparsick.ansible.docker.demo.webapp.domain.Person;
import java.util.List;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 *
 * @author sparsick
 */
@Testcontainers
class PersonRepositoryITest {

    @Container
    private MySQLContainer mysqlDb = new MySQLContainer();

    @Test
    void saveAndLoadAPerson() {
        FluentConfiguration dbConfig = Flyway.configure()
                .dataSource(mysqlDb.getJdbcUrl(), mysqlDb.getUsername(), mysqlDb.getPassword());
        Flyway flyway = new Flyway(dbConfig);
        flyway.migrate();
        
        DataSource dataSource = flyway.getConfiguration().getDataSource();
        PersonRepository personRepositoryUnderTest = new PersonRepository(dataSource);
        Person person = new Person("Alice", "Bob");
        personRepositoryUnderTest.save(person);
        
        List<Person> persons = personRepositoryUnderTest.findAllPersons();
        
        assertThat(persons.size(), Is.is(1));
        assertThat(persons.get(0), Is.is(person));
    }

}
