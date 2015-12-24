package com.resources.auth;
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

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
            ((UserAuthority) authorities.iterator().next()).setUser(admin);
            if (!userService.get("Admin").isEmpty()) {
                return;
            }
            userService.add(admin);
            System.out.println("INFOCUSTOM: CREATED USER Admin");
            System.out.println("INFOCUSTOM: PASSWORD FOR Admin is " + sb.toString());
            System.err.println("INFOCUSTOM: CREATED USER Admin");
            System.err.println("INFOCUSTOM: PASSWORD FOR Admin is " + sb.toString());

            Set<GrantedAuthority> userAuthorities = new HashSet<GrantedAuthority>();
            userAuthorities.add(new UserAuthority("ROLE_USER"));
            if (!userService.get("123").isEmpty()) {
                return;
            }
            User user = new User("123", encoder.encode("123"), userAuthorities);
            ((UserAuthority) authorities.iterator().next()).setUser(user);
            userService.add(user);
            System.out.println("INFOCUSTOM: CREATED USER 123 PASSWORD 123");
            System.err.println("INFOCUSTOM: CREATED USER 123 PASSWORD 123");

        }
    }
}
