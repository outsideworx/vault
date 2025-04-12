package application.configuration.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FilterConditions {
    public boolean notPreflightRequest(HttpServletRequest request) {
        return !"OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    // TODO: Today with any single valid token you have access to all client endpoints.
    public boolean invalidAuthToken(HttpServletRequest request, String... tokens) {
        String requestToken = request.getHeader("X-Auth-Token");
        return Arrays.stream(tokens).noneMatch(token -> token.equals(requestToken));
    }
}