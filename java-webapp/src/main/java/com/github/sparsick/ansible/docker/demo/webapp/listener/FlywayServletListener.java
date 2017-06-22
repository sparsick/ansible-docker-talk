package com.github.sparsick.ansible.docker.demo.webapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * 
 */
public class FlywayServletListener implements ServletContextListener{
    
    @Autowired
    private Flyway flyway;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContextUtils
            .getRequiredWebApplicationContext(sce.getServletContext())
            .getAutowireCapableBeanFactory()
            .autowireBean(this);
        
        flyway.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Do nothing
    }

}
