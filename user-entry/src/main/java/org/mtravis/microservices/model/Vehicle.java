package org.mtravis.microservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vehicle {
    @JsonProperty("license_number")
    String licenseNumber;
    @JsonProperty("vehicle_size")
    VehicleType type;
}
