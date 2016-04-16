package com.resources.auth.Config;

import com.resources.auth.AppInit;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by tanvd on 12.04.16.
 */
public class WebAppInit implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Root context contains database objects and appinit
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.scan("com.resources.auth.Config.DatabaseConfig");
        rootContext.scan("com.resources.auth.Database");
        rootContext.register(AppInit.class);

        //servletContext.addListener(Log4j.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        //Dispatcher context contains controllers objects, security objects and mvc config
        //Moving security to root creates problems (double creating) and logically security takes to dispatcher
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.scan("com.resources.auth.Controllers");
        dispatcherContext.scan("com.resources.auth.Config.SecurityConfig");
        dispatcherContext.scan("com.resources.auth.Security");
        dispatcherContext.scan("com.resources.auth.Config.OAuth2Config");
        dispatcherContext.register(WebMVCConfig.class);
        dispatcherContext.setParent(rootContext);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
        filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");

        servletContext.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");
    }
}
