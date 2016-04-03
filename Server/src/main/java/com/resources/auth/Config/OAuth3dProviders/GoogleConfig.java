package com.resources.auth.Config.OAuth3dProviders;

import com.racquettrack.security.oauth.*;
import com.resources.auth.Config.OAuth2UserDetailsLoaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanvd on 03.04.16.
 */
@Configuration
public class GoogleConfig {
    @Configuration
    @PropertySource("classpath:oauthGoogle.properties")
    protected static class OAuthAuthentication {


        @Bean
        public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean(name = "propertiesGoogle")
        public OAuth2ServiceProperties oAuth2ServicePropertiesGoogle(
                @Value("${accessTokenUri}") String accessTokenUri,
                @Value("${userAuthorizationUri}") String userAuthorizationUri,
                @Value("${userInfoUri}") String userInfoUri,
                @Value("${clientId}") String clientId,
                @Value("${clientSecret}") String clientSecret,
                @Value("${redirectURI}") String redirectURI,
                @Value("${scope}") String scope
        ) throws URISyntaxException {
            OAuth2ServiceProperties details = new OAuth2ServiceProperties();
            details.setUserAuthorisationUri(userAuthorizationUri);
            details.setRedirectUri(redirectURI);
            details.setAccessTokenUri(accessTokenUri);
            details.setClientId(clientId);
            details.setClientSecret(clientSecret);
            details.setUserInfoUri(userInfoUri);
            details.setUserIdName("email");

            Map<String, String> additionalParams = new HashMap<String, String>();
            additionalParams.put("scope", scope);
            details.setAdditionalAuthParams(additionalParams);

            return details;
        }



        @Bean
        @Autowired
        public OAuth2UserDetailsService<User> oAuth2UserDetailsServiceGoogle(OAuth2ServiceProperties serviceProperties,
                                                                       OAuth2UserDetailsLoader detailsLoader) throws URISyntaxException {
            OAuth2UserDetailsService service = new OAuth2UserDetailsService();
            service.setoAuth2ServiceProperties(serviceProperties);
            service.setoAuth2UserDetailsLoader(detailsLoader);
            DefaultOAuth2UserInfoProvider provider = new DefaultOAuth2UserInfoProvider();
            provider.setoAuth2ServiceProperties(serviceProperties);
            service.setoAuth2UserInfoProvider(provider);
            return service;
        }

        @Bean(name = "providerGoogle")
        @Autowired
        public OAuth2AuthenticationProvider oAuth2AuthenticationProviderGoogle(OAuth2ServiceProperties serviceProperties,
                                                                         OAuth2UserDetailsService<User> details) throws URISyntaxException {
            OAuth2AuthenticationProvider authProv = new OAuth2AuthenticationProvider();
            authProv.setAuthenticatedUserDetailsService(details);
            authProv.setoAuth2ServiceProperties(serviceProperties);
            return authProv;
        }

        @Bean(name = "entryPointGoogle")
        @Autowired
        public OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPointGoogle(OAuth2ServiceProperties serviceProperties) throws URISyntaxException {
            OAuth2AuthenticationEntryPoint entry = new OAuth2AuthenticationEntryPoint();
            entry.setoAuth2ServiceProperties(serviceProperties);
            return entry;
        }
    }

    @Configuration
    protected static class OAuthGoogleAuth extends WebSecurityConfigurerAdapter {

        @Resource(name = "filterGoogle")
        OAuth2AuthenticationFilter filterGoogle;

        @Resource(name = "entryPointGoogle")
        OAuth2AuthenticationEntryPoint oAuthEntryPointGoogle;

        @Bean(name = "filterGoogle")
        @Autowired
        public OAuth2AuthenticationFilter oAuth2AuthenticationFilter(OAuth2ServiceProperties serviceProperties,
                                                                     AuthenticationManager manager) throws Exception {
            OAuth2AuthenticationFilter filter = new OAuth2AuthenticationFilter("/oauth/callback/google");
            filter.setAuthenticationManager(manager);
            filter.setoAuth2ServiceProperties(serviceProperties);
            return filter;
        }


        //.csrf() is optional, enabled by default, if using WebSecurityConfigurerAdapter constructor
        @Override
        @DependsOn("filterGoogle")
        protected void configure(HttpSecurity httpSecurity) throws Exception {

            httpSecurity.authorizeRequests()
                    .antMatchers("/oauth/callback/google").permitAll()
                    .antMatchers("/oauth/google").authenticated()

                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(oAuthEntryPointGoogle)
                    .and()
                    .addFilterAfter(filterGoogle, ExceptionTranslationFilter.class)
                    .csrf().disable();

        }
    }
}
