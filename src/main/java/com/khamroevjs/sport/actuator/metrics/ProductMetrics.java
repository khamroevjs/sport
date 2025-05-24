package com.khamroevjs.sport.actuator.metrics;

import com.khamroevjs.sport.repository.ProductRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ProductMetrics {

    public ProductMetrics(MeterRegistry meterRegistry, ProductRepository productRepository) {
        Gauge.builder("product_count", productRepository::count)
                .description("Number of products")
                .register(meterRegistry);
    }
}
