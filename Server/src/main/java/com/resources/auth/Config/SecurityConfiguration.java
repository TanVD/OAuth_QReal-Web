package com.resources.auth.Config;
import com.racquettrack.security.oauth.OAuth2AuthenticationProvider;
import com.resources.auth.Database.Users.UserDAO;
import com.resources.auth.Database.Users.UserDAOSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.lang.model.element.Name;

@Configuration
@EnableWebSecurity
@Order(2) //EnableResourceServer annotation create WebSecurityConfigurerAdapter with Order(3)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource(name = "userServiceSec")
    UserDAOSec userServiceSec;

    @Resource(name = "passwordEncoder")
    PasswordEncoder encoder;

    @Resource(name = "providerGoogle")
    OAuth2AuthenticationProvider oAuthProvGoogle;

    @Resource(name = "providerGithub")
    OAuth2AuthenticationProvider oAuthProvGithub;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(oAuthProvGoogle);
        auth.authenticationProvider(oAuthProvGithub);
        auth.userDetailsService(userServiceSec).passwordEncoder(encoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .requestMatchers()
                .antMatchers("/*", "/tableRegistered/**", "/servers/**", "/register/**", "/resources/**", "/oauth/authorize")
                .and()

                .exceptionHandling()
                .accessDeniedPage("/logErr")
                .and()

                .authorizeRequests()
                .antMatchers("/oauth/authorize")
                .hasRole("USER")
                .and()

                .authorizeRequests()
                .antMatchers("/tableRegistered/**")
                .hasRole("ADMIN")
                .and()

                .authorizeRequests()
                .antMatchers("/servers/**")
                .hasRole("ADMIN")
                .and()

                .authorizeRequests()
                .antMatchers("/userServers")
                .hasRole("USER")
                .and()

                .authorizeRequests()
                .antMatchers("/tableRegistered/**")
                .hasRole("ADMIN")
                .and()

                .authorizeRequests()
                .antMatchers("/logErr")
                .permitAll()
                .and()

                .authorizeRequests()
                .antMatchers( "/register**")
                .permitAll()
                .and()

                .authorizeRequests()
                .antMatchers("/resources/**")
                .permitAll()
                .and()

                // TODO: put CSRF protection back into this endpoint
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()

                .formLogin()
                .loginPage("/")
                .failureUrl("/logErr")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/routeRole")
                .loginProcessingUrl("/login");
        // @formatter:on
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

}