package com.fih.ishareing.configurations.security;

import java.util.Arrays;

import com.fih.ishareing.configurations.filter.AuthenticationTokenFilter;
import com.fih.ishareing.service.token.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2Sha256PasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors();
        // enable xssProtection
        http.headers().xssProtection();

        http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
        // disable page caching
        http.headers().cacheControl();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new AuthenticationTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/banners","/api/v1/events","/api/v1/events/volunteer","/api/v1/events/enterprise","/api/v1/events/supply","/api/v1/npos","/api/v1/npos/promote","/api/v1/donationNpos","/api/v1/npos/menu","/api/v1/auth/sign","/api/v1/appConfigs","/api/v1/events/menu").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/auth/token", "/api/v1/reset/password", "/api/v1/auth/fubon", "/api/v1/auth/twm", "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/auth/token", "/api/v1/reset/password").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().authenticated();
        // @formatter:on

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "AccessKeyid",
                "Authorization", "Signature", "SignatureMethod", "SignatureNonce", "SignatureVersion", "Timestamp",
                "Version", "X-Request-ID", "X-Access-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}