package com.resources.auth;
import com.resources.auth.database.Users.User;
import com.resources.auth.database.Users.UserAuthority;
import com.resources.auth.database.Users.UserDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AppInit implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
            UserDAO userService = (UserDAO) applicationContext.getBean("userService");
            PasswordEncoder encoder = (PasswordEncoder) applicationContext.getBean("passwordEncoder");
            int length = 12;
            Random r = new Random();
            StringBuilder sb = new StringBuilder();
            String randAlph = "qwertyuiop[]asdfghjkl;zxcvbnm,./QWERTYUIOP[]ASDFGHJKL;ZXCVBNM,./1234567890-!@#$%^&*()";
            for(int i = 0; i < length; i++) {
                char c = randAlph.charAt(r.nextInt(randAlph.length()));
                sb.append(c);
            }
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new UserAuthority("ROLE_ADMIN"));
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
        }
    }
}
