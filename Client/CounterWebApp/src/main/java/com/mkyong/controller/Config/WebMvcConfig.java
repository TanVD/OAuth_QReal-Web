package com.mkyong.controller.Config;


import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.mkyong.controller.Config.OAuthFirstStep.OAuth2UserDetailsLoaderDet;
import com.racquettrack.security.oauth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Service;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.mkyong.controller.SparklrService;
import com.mkyong.controller.AccessTokenRequestConverter;

import javax.annotation.Resource;

@Configuration
@EnableWebMvc
@ComponentScan("com.mkyong")
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    //First step auth
    private String accessTokenUri = "http://localhost:8080/oauth/token";

    private String userAuthorizationUri = "http://localhost:8080/oauth/authorize";

    private String userInfoUri = "http://localhost:8080/userInfo";


    @Bean (name = "properties")
    public OAuth2ServiceProperties oAuth2ServiceProperties() throws URISyntaxException {
        OAuth2ServiceProperties details = new OAuth2ServiceProperties();
        details.setUserAuthorisationUri(userAuthorizationUri);
        details.setRedirectUri("/oauth/callback");
        details.setAccessTokenUri(accessTokenUri);
        details.setClientId("tonr");
        details.setClientSecret("secret");
        details.setUserInfoUri(userInfoUri);
        return details;
    }

    @Bean(name = "loader")
    public OAuth2UserDetailsLoader<User, SimpleGrantedAuthority> oAuth2UserDetailsLoader() {
        return new OAuth2UserDetailsLoaderDet();
    }

    @Bean(name = "details")
    @DependsOn("properties")
    public OAuth2UserDetailsService<User> oAuth2UserDetailsService() throws URISyntaxException {
        OAuth2UserDetailsService service = new OAuth2UserDetailsService();
        service.setoAuth2ServiceProperties(oAuth2ServiceProperties());
        service.setoAuth2UserDetailsLoader(oAuth2UserDetailsLoader());
        OAuth2UserInfoProvider provider = new DefaultOAuth2UserInfoProvider();
        service.setoAuth2UserInfoProvider(provider);
        return service;
    }

    @Bean(name = "provider")
    @DependsOn("details")
    public OAuth2AuthenticationProvider oAuth2AuthenticationProvider() throws URISyntaxException {
        OAuth2AuthenticationProvider authProv = new OAuth2AuthenticationProvider();
        authProv.setAuthenticatedUserDetailsService(oAuth2UserDetailsService());
        authProv.setoAuth2ServiceProperties(oAuth2ServiceProperties());
        return authProv;
    }

    @Bean
    public OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint() throws URISyntaxException {
        OAuth2AuthenticationEntryPoint entry = new OAuth2AuthenticationEntryPoint();
        entry.setoAuth2ServiceProperties(oAuth2ServiceProperties());
        return entry;
    }


    //First step th


    @Bean
    public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
        ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);
        contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
        contentViewResolver.setDefaultViews(Arrays.<View>asList(new MappingJackson2JsonView()));
        return contentViewResolver;
    }

   @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
       }

}