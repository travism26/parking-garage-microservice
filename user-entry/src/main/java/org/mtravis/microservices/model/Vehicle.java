package org.mtravis.microservices.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vehicle {
    String licenseNumber;
    VehicleType type;

    public enum VehicleType {
        SMALL, MEDIUM, LARGE, EXTRA_LARGE
    }
}
