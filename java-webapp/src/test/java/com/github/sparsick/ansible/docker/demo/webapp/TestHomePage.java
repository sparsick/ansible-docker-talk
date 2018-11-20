package com.github.sparsick.ansible.docker.demo.webapp;

import com.github.sparsick.ansible.docker.demo.webapp.domain.Person;
import com.github.sparsick.ansible.docker.demo.webapp.repository.PersonRepository;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple test using the WicketTester
 */
class TestHomePage {

    private WicketTester tester;

    private ApplicationContextMock applicationContextMock;


    @BeforeEach
    void setUp() {
        applicationContextMock = new ApplicationContextMock();
        WicketApplication wicketApp = new WicketApplication();
        wicketApp.getComponentInstantiationListeners()
        .add(new SpringComponentInjector(wicketApp,applicationContextMock));
        tester = new WicketTester(wicketApp);

        applicationContextMock.putBean(new PersonRepository(){
            @Override
            public List<Person> findAllPersons() {
              return Collections.emptyList();
            }
        });

    }


    @Test
    void homepageRendersSuccessfully() {
        // start and render the test page
        tester.startPage(HomePage.class);

        // assert rendered page class
        tester.assertRenderedPage(HomePage.class);
    }
}
