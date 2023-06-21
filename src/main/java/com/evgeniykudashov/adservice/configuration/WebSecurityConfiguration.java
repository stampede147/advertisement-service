package com.evgeniykudashov.adservice.configuration;


import com.evgeniykudashov.adservice.security.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@AllArgsConstructor(onConstructor_ = @Autowired)
@ConditionalOnBean(SecurityComponentConfiguration.class)
public class WebSecurityConfiguration {

    private final String[] API_AVAILABLE_UNAUTHORIZED_PATHS = {"/advertisements/**", "/authentication"};
    protected JwtAuthenticationFilter filter;


    @Bean()
    @SneakyThrows
    protected SecurityFilterChain filterChain(HttpSecurity http) {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .securityContext().and()
                .authorizeHttpRequests(r -> {
                    r.anyRequest().permitAll();
//                    r.requestMatchers(API_AVAILABLE_UNAUTHORIZED_PATHS).permitAll();
//                    r.anyRequest().authenticated();
                })
                .addFilterBefore(filter, AnonymousAuthenticationFilter.class)
                .build();
    }


}
