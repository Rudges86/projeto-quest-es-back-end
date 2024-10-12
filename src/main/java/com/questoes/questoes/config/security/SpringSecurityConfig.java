package com.questoes.questoes.config.security;

import com.questoes.questoes.config.security.jwt.JwtAuthenticationEntryPoint;
import com.questoes.questoes.config.security.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class SpringSecurityConfig  {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        //Libera os frames do h2
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.cors().and()
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/cadastrar")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/recuperarUsuario")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/validar/{uuid}")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/alterarSenhaObrigatorio/{id}")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.PATCH, "/api/alterarSenhaObrigatorio/{id}")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/auth/login")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/auth/validate-token")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/questoes")).permitAll()
                        .requestMatchers(
                                antMatcher("/docs-park.html"),
                                antMatcher("/docs-park/**"),
                                antMatcher("/swagger-ui.html"),
                                antMatcher("/swagger-ui/**"),
                                antMatcher("/webjars/**"),
                                antMatcher("/h2-console/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                ).build();
    }

    @Bean
    public MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/");
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
