package com.jaax.springsecurity.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {//SecurityFilterChain es una cadena de seguridad donde se van ejecutando el filtrado y la autenticación
//Se define la seguridad que se le va a aplicar a los endpoints
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()//Se define que métodos son públicos, lista blanca
                        .requestMatchers("/api/users").hasAuthority("ROLE_ADMIN")  // Uso de requestMatchers en lugar de antMatchers
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//UsernamePasswordAuthenticationFilter es un filtro que se ejecuta antes de JwtFilter y es propia de Spring Security

        return http.build();
    }


    private RequestMatcher publicEndpoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/greeting/hello"),
                new AntPathRequestMatcher("/api/auth/**")
             //   new AntPathRequestMatcher("/api/users/**")
                // Aquí puedes agregar más matchers para otras rutas públicas si es necesario
        );
    }
}
