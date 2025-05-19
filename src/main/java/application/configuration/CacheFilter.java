package application.configuration;

import application.configuration.utils.FilterConditions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
final class CacheFilter extends HttpFilter {
    private final FilterConditions filterConditions;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (filterConditions.notPreflightRequest(request) && request.getRequestURI().startsWith("/api/cached")) {
            response.setHeader("Cache-Control", "public, max-age=86400");
        }
        chain.doFilter(request, response);
    }
}