package com.jn.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

@Service
public class HealthStatusCheck implements HealthIndicator {

    private Boolean status = true;


    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    @Override
    public Health health() {

        if (status) {
            return new Health.Builder().up().build();
        }

        return new Health.Builder().down().build();
    }
}
