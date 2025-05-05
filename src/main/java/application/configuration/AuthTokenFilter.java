package application.configuration;

import application.configuration.utils.FilterConditions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
class AuthTokenFilter extends HttpFilter {
    @Value("${app.clients.ciafo.token}")
    private String ciafoAuthToken;

    private final FilterConditions filterConditions;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (filterConditions.notPreflightRequest(request) && request.getRequestURI().startsWith("/api")) {
            if (filterConditions.invalidAuthToken(request, ciafoAuthToken)) {
                log.error("Invalid auth token for request: [{}]", request.getRequestURL());
                throw new BadCredentialsException("Invalid auth token.");
            }
        }
        chain.doFilter(request, response);
    }
}