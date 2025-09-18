package application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class WebSecurity {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/**", "/img/**", "/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(request -> request
                        .loginPage("/login"))
                .exceptionHandling(request -> request
                        .accessDeniedPage("/login?error"))
                .sessionManagement(request -> request
                        .invalidSessionUrl("/login?expired"))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}