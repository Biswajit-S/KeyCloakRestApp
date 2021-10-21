/**
 * 
 */
package com.example.KeyCloakRestApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import com.example.KeyCloakRestApp.util.AppProperties;

/**
 * @author biswajit
 *
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Security configurations for OAuth2 is done here.
	 * APIs with the below path required to present an valid Bearer token, before the request is processed.
	 * 
	 * Also checks for scope of the access token to be "scope_movies_app_access"
	 *
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers(HttpMethod.GET, "/finder/**").hasAuthority("scope_movies_app_access")
                .antMatchers(HttpMethod.POST, "/rating/**").hasAuthority("scope_movies_app_access")
                .anyRequest().authenticated()).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
    
    /**
	 * Service method to decode the JWT.
	 *
	 */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(AppProperties.getJwkUri()).build();
    }

}
