package com.resources.auth;
import com.resources.auth.Database.Client.Client;
import com.resources.auth.Database.Client.ClientDAO;
import com.resources.auth.Database.Users.User;
import com.resources.auth.Database.Users.UserAuthority;
import com.resources.auth.Database.Users.UserDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This is class of application initialization.
 * It creates admin account and password for it. Also it creates default user 123.
 * @author TanVD
 */
@Component
public class AppInit implements ApplicationListener {

    @EventListener
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();

            UserDAO userService = (UserDAO) applicationContext.getBean("userService");
            PasswordEncoder encoder = (PasswordEncoder) applicationContext.getBean("passwordEncoder");

            int length = 12;
            Random r = new Random();
            StringBuilder sb = new StringBuilder();
            String randAlph = "qwertyuiop[]asdfghjklzxcvbnm,./QWERTYUIOP[]ASDFGHJKLZXCVBNM1234567890-!@#$%^&*()";

            for(int i = 0; i < length; i++) {
                char c = randAlph.charAt(r.nextInt(randAlph.length()));
                sb.append(c);
            }

            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new UserAuthority("ROLE_ADMIN"));
            authorities.add(new UserAuthority("ROLE_USER"));
            User admin = new User("Admin", encoder.encode(sb.toString()), authorities);

            if (!userService.get("Admin").isEmpty()) {
                return;
            }
            userService.add(admin);

            broadcastNewUser("Admin", sb.toString());

            Set<GrantedAuthority> userAuthorities = new HashSet<GrantedAuthority>();
            userAuthorities.add(new UserAuthority("ROLE_USER"));
            User user = new User("123", encoder.encode("123"), userAuthorities);

            if (!userService.get("123").isEmpty()) {
                return;
            }
            userService.add(user);

            broadcastNewUser("123", "123");


            ClientDAO clientService = (ClientDAO) applicationContext.getBean("clientService");

            Set<String> scopes = new HashSet<String>();
            scopes.add("read");
            scopes.add("write");

            Set<String> grantTypes = new HashSet<String>();
            grantTypes.add("authorization_code");

            Client robotsDiagram = new Client("robotsDiagram", true, "secret", true, scopes, grantTypes,
                    64000, 64000, true);
            clientService.add(robotsDiagram);
        }
    }

    private void broadcastNewUser(String name, String password)
    {
        System.out.println("INFOCUSTOM: CREATED USER " + name);
        System.out.println("INFOCUSTOM: PASSWORD FOR " + name + " is " + password);
        System.err.println("INFOCUSTOM: CREATED USER " + name);
        System.err.println("INFOCUSTOM: PASSWORD FOR " + name + " is " + password);
    }
}
