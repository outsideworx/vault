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
                .authorizeHttpRequests(httpRequest -> httpRequest
                        .requestMatchers("/img/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(httpRequest -> httpRequest
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home"))
                .logout(httpRequest -> httpRequest
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true))
                .sessionManagement(httpRequest -> httpRequest
                        .invalidSessionUrl("/login?expired"))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}