package com.mkyong.controller.Config;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.mkyong.controller.SparklrService;
import com.mkyong.controller.AccessTokenRequestConverter;

@Configuration
@EnableWebMvc
@PropertySource("classpath:oauth.properties")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

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



    @Bean
    public SparklrService sparklrService(@Value("${sparklrPhotoListURL}")
                                             String sparklrPhotoListURL, @Value("${sparklrPhotoURLPattern}")
                                             String sparklrPhotoURLPattern, @Value("${sparklrTrustedMessageURL}")
                                             String sparklrTrustedMessageURL, @Value("${userService}")
                                             String userService             ,  @Qualifier("sparklrRestTemplate")
                                             RestOperations sparklrRestTemplate, @Qualifier("trustedClientRestTemplate")
                                             RestOperations trustedClientRestTemplate) {
        SparklrService sparklrService = new SparklrService();
        sparklrService.setSparklrPhotoListURL(sparklrPhotoListURL);
        sparklrService.setSparklrPhotoURLPattern(sparklrPhotoURLPattern);
        sparklrService.setSparklrTrustedMessageURL(sparklrTrustedMessageURL);
        sparklrService.setOurUserServersUrl(userService);
        sparklrService.setSparklrRestTemplate(sparklrRestTemplate);
        sparklrService.setTrustedClientRestTemplate(trustedClientRestTemplate);
        return sparklrService;
    }

    @Bean
    public SparklrService sparklrRedirectService(@Value("${sparklrPhotoListURL}")
                                                     String sparklrPhotoListURL, @Value("${sparklrPhotoURLPattern}")
                                                     String sparklrPhotoURLPattern, @Value("${sparklrTrustedMessageURL}")
                                                     String sparklrTrustedMessageURL, @Value("${userService}")
                                                     String userService             ,  @Qualifier("sparklrRedirectRestTemplate")
                                                     RestOperations sparklrRestTemplate, @Qualifier("trustedClientRestTemplate")
                                                     RestOperations trustedClientRestTemplate) {
        SparklrService sparklrService = new SparklrService();
        sparklrService.setSparklrPhotoListURL(sparklrPhotoListURL);
        sparklrService.setSparklrPhotoURLPattern(sparklrPhotoURLPattern);
        sparklrService.setSparklrTrustedMessageURL(sparklrTrustedMessageURL);
        sparklrService.setOurUserServersUrl(userService);
        sparklrService.setSparklrRestTemplate(sparklrRestTemplate);
        sparklrService.setTrustedClientRestTemplate(trustedClientRestTemplate);
        return sparklrService;
    }

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean conversionService = new ConversionServiceFactoryBean();
        conversionService.setConverters(Collections.singleton(new AccessTokenRequestConverter()));
        return conversionService;
    }

//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//    }
//    don't think it is necessary

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }

    @Configuration
    @EnableOAuth2Client
    protected static class ResourceConfiguration {

        @Value("${accessTokenUri}")
        private String accessTokenUri;

        @Value("${userAuthorizationUri}")
        private String userAuthorizationUri;

        @Bean
        public OAuth2ProtectedResourceDetails sparklr() {
            AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
            details.setId("sparklr/tonr");
            details.setClientId("tonr");
            details.setClientSecret("secret");
            details.setAccessTokenUri(accessTokenUri);
            details.setUserAuthorizationUri(userAuthorizationUri);
            details.setScope(Arrays.asList("read", "write"));
            return details;
        }

        @Bean
        public OAuth2ProtectedResourceDetails sparklrRedirect() {
            AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
            details.setId("sparklr/tonr-redirect");
            details.setClientId("tonr-with-redirect");
            details.setClientSecret("secret");
            details.setAccessTokenUri(accessTokenUri);
            details.setUserAuthorizationUri(userAuthorizationUri);
            details.setScope(Arrays.asList("read", "write"));
            details.setUseCurrentUri(false);
            return details;
        }

        @Bean
        public OAuth2ProtectedResourceDetails trusted() {
            ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
            details.setId("sparklr/trusted");
            details.setClientId("my-client-with-registered-redirect");
            details.setAccessTokenUri(accessTokenUri);
            details.setScope(Arrays.asList("trust"));
            return details;
        }

        @Bean
        public OAuth2RestTemplate sparklrRestTemplate(OAuth2ClientContext clientContext) {
            return new OAuth2RestTemplate(sparklr(), clientContext);
        }

        @Bean
        public OAuth2RestTemplate sparklrRedirectRestTemplate(OAuth2ClientContext clientContext) {
            return new OAuth2RestTemplate(sparklrRedirect(), clientContext);
        }

        @Bean
        public OAuth2RestTemplate trustedClientRestTemplate() {
            return new OAuth2RestTemplate(trusted(), new DefaultOAuth2ClientContext());
        }

    }

}