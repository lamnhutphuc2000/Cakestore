/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.listener;

import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author ASUS
 */
@WebListener()
public class MyServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("phucln.properties.sitemap");
        context.setAttribute("SITE_MAP", resourceBundle);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
