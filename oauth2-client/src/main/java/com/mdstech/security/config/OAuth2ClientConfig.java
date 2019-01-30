package com.mdstech.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {

    //TODO: Ref https://github.com/mariubog/oauth-client-sample/

    @Autowired(required = false)
    ClientHttpRequestFactory clientHttpRequestFactory;

    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

//    @Value("${security.oauth2.client.userAuthorizationUri}")
//    private String userAuthorizationUri;

    @Value("${security.oauth2.client.clientId}")
    private String clientID;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Bean
    public AccessTokenProvider userAccessTokenProvider() {
        ResourceOwnerPasswordAccessTokenProvider accessTokenProvider = new ResourceOwnerPasswordAccessTokenProvider();
        accessTokenProvider.setRequestFactory(getClientHttpRequestFactory());
        return accessTokenProvider;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        if (clientHttpRequestFactory == null) {
            clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        }
        return clientHttpRequestFactory;
    }

    @Bean
    public OAuth2RestOperations restTemplate() {

        OAuth2RestTemplate template = new OAuth2RestTemplate(oAuth2ProtectedResourceDetails(), new DefaultOAuth2ClientContext(
                new DefaultAccessTokenRequest()));
        return prepareTemplate(template, false);
    }

    public OAuth2RestTemplate prepareTemplate(OAuth2RestTemplate template, boolean isClient) {
        template.setRequestFactory(getClientHttpRequestFactory());
        if (isClient) {
//            template.setAccessTokenProvider(clientAccessTokenProvider());
        } else {
            template.setAccessTokenProvider(userAccessTokenProvider());
        }
        return template;
    }

    @Bean
    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(accessTokenUri);
        resource.setClientId(clientID);
        resource.setClientSecret(clientSecret);
        resource.setGrantType("password");
        resource.setUsername("user1");
        resource.setPassword("password");
        return resource;

//        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
//        details.setId("resource1");
//        details.setClientId(clientID);
//        details.setClientSecret(clientSecret);
//        details.setAccessTokenUri(accessTokenUri);
//        details.setUserAuthorizationUri(userAuthorizationUri);
//        details.setTokenName("oauth_token");
//        details.setScope(Arrays.asList("identity"));
//        details.setPreEstablishedRedirectUri("http://localhost/login");
//        details.setUseCurrentUri(false);
//        return details;
    }

//    @Bean
//    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
//                                                 OAuth2ProtectedResourceDetails details) {
//        return new OAuth2RestTemplate(details, oauth2ClientContext);
//    }

    @Bean
    public OAuth2ClientContext oAuth2ClientContext() {
        return new DefaultOAuth2ClientContext();
    }
}
