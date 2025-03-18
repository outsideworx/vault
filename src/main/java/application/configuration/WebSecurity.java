package application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                        .requestMatchers("/login", "/img/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(request -> request
                        .loginPage("/login"))
                .sessionManagement(request -> request
                        .invalidSessionUrl("/login?expired")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}