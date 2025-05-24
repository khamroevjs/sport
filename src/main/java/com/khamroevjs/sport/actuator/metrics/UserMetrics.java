package com.khamroevjs.sport.actuator.metrics;

import com.khamroevjs.sport.model.UserRole;
import com.khamroevjs.sport.repository.UserRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserMetrics {

    public UserMetrics(MeterRegistry meterRegistry, UserRepository userRepository) {
        Gauge.builder("user_with_admin_role", () -> userRepository.countAllByRoleIs(UserRole.ADMIN))
                .description("Number of users with ADMIN role")
                .register(meterRegistry);

        Gauge.builder("user_with_user_role", () -> userRepository.countAllByRoleIs(UserRole.USER))
                .description("Number of users with USER role")
                .register(meterRegistry);
    }
}
