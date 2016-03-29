package com.mkyong.controller.Config;

import com.racquettrack.security.oauth.OAuth2AuthenticationEntryPoint;
import com.racquettrack.security.oauth.OAuth2AuthenticationFilter;
import com.racquettrack.security.oauth.OAuth2AuthenticationProvider;
import com.racquettrack.security.oauth.OAuth2ServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URISyntaxException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2AuthenticationProvider oauthProv;

    @Autowired
    OAuth2AuthenticationEntryPoint oauthAuthenticationEntryPoint;

    @Autowired
    OAuth2AuthenticationFilter filter;

    @Resource(name = "properties")
    OAuth2ServiceProperties serviceProperties;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(oauthProv);
    }

    @Bean(name = "manager")
    @DependsOn("provider")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @DependsOn("manager")
    public OAuth2AuthenticationFilter oAuth2AuthenticationFilter() throws Exception {
        OAuth2AuthenticationFilter filter = new OAuth2AuthenticationFilter("/oauth/callback");
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setoAuth2ServiceProperties(serviceProperties);
        return filter;
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

                .httpBasic()

                .authenticationEntryPoint(oauthAuthenticationEntryPoint)
                .and()

                .addFilterAfter(filter, ExceptionTranslationFilter.class)


                .formLogin()
                .loginProcessingUrl("/")
                .loginPage("/")
                .defaultSuccessUrl("/CounterWebApp")
                .loginProcessingUrl("/login");
        // @formatter:on
    }


}

