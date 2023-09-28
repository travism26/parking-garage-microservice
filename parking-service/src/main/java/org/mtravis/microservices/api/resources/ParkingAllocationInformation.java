package org.mtravis.microservices.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.mtravis.microservices.api.model.Vehicle;

@Data
@Builder
public class ParkingAllocationInformation {
    @JsonProperty("parking_spot")
    public long parkingSpot;
    @JsonProperty("parking_type")
    public Vehicle.VehicleType spotType;

    @Override
    public String toString() {
        return "ParkingAllocationInformation{" +
                "parkingSpot=" + parkingSpot +
                ", spotType=" + spotType +
                '}';
    }
}

