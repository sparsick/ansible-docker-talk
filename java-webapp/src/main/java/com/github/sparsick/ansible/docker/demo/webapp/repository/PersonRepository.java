package com.github.sparsick.ansible.docker.demo.webapp.repository;

import com.github.sparsick.ansible.docker.demo.webapp.domain.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class PersonRepository {

    private JdbcTemplate jdbcTemplate;


    @Autowired(required = true)
    public PersonRepository(DataSource dataSource) {
        checkJdbcDriver();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PersonRepository() {

    }


    public List<Person> findAllPersons() {
        return jdbcTemplate.query("Select * from person;", new RowMapper<Person>() {

            @Override
            public Person mapRow(ResultSet resultSet, int arg1) throws SQLException {
                Person person = new Person();
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setJobTitle(resultSet.getString("job_title"));
                return person;
            }

        });

    }


    public void save(Person person) {
//        jdbcTemplate.update("Insert into person (first_name,last_name) values(?,?)", person.getFirstName(),
//                person.getLastName());
        
        
        jdbcTemplate.update("Insert into person (first_name,last_name,job_title) values(?,?,?)", person.getFirstName(),
                person.getLastName(), person.getJobTitle());
    }

    private void checkJdbcDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            throw new RuntimeException("Cannot load JDBC driver", ex);
        }
    }
}
