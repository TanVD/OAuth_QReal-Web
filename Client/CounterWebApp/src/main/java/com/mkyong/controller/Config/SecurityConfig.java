package com.mkyong.controller.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("marissa").password("wombat").roles("USER").and().withUser("sam")
                .password("kangaroo").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/sparklr/**", "/facebook/**", "/getMyLogin", "/CounterWebApp/", "/table")
                .hasRole("USER")
                .and()

                .authorizeRequests()
                .antMatchers("/resources/**")
                .permitAll()
                .and()

                .formLogin()
                .loginProcessingUrl("/")
                .loginPage("/")
                .defaultSuccessUrl("/CounterWebApp")
                .loginProcessingUrl("/login");
        // @formatter:on
    }
}

