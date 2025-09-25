package application.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public final class GrafanaService {
    private final MeterRegistry registry;

    public void registerRequest(String... tags) {
        Counter.builder("vault_requests")
                .tags(tags)
                .register(registry)
                .increment();
    }
}