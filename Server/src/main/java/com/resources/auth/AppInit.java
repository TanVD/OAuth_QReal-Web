package com.resources.auth;
import com.resources.auth.Database.Client.Client;
import com.resources.auth.Database.Client.ClientDAO;
import com.resources.auth.Database.Users.User;
import com.resources.auth.Database.Users.UserAuthority;
import com.resources.auth.Database.Users.UserDAO;
import com.resources.auth.Security.Utils.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * This is class of application initialization.
 * It creates admin account and password for it. Also it creates default user 123.
 * @author TanVD
 */
@Component
public class AppInit implements ApplicationListener {

    private static final Logger logger = LoggerFactory.getLogger(AppInit.class);

    @EventListener
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();

            UserDAO userService = (UserDAO) applicationContext.getBean("userService");
            PasswordEncoder encoder = (PasswordEncoder) applicationContext.getBean("passwordEncoder");



            if (!userService.get("Admin").isEmpty()) {
                return;
            }

            String password = RandomStringGenerator.generateString(12);
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new UserAuthority("ROLE_ADMIN"));
            authorities.add(new UserAuthority("ROLE_USER"));
            User admin = new User("Admin", encoder.encode(password), authorities);

            userService.add(admin);

            logger.info("CREATED USER {} WITH PASSWORD {}", "Admin", password);

            if (!userService.get("123").isEmpty()) {
                return;
            }

            Set<GrantedAuthority> userAuthorities = new HashSet<GrantedAuthority>();
            userAuthorities.add(new UserAuthority("ROLE_USER"));
            User user = new User("123", encoder.encode("123"), userAuthorities);

            userService.add(user);

            logger.info("CREATED USER {} WITH PASSWORD {}", "123", "123");

            ClientDAO clientService = (ClientDAO) applicationContext.getBean("clientService");

            Set<String> scopes = new HashSet<String>();
            scopes.add("read");
            scopes.add("write");

            Set<String> grantTypes = new HashSet<String>();
            grantTypes.add("authorization_code");

            Client robotsDiagram = new Client("robotsDiagram", true, "secret", true, scopes, grantTypes,
                    64000, 64000, true);
            clientService.add(robotsDiagram);
            logger.info("Initalized");
        }
    }
}
