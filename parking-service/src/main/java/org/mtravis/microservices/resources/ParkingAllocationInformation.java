package org.mtravis.microservices.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.mtravis.microservices.model.ParkingSpotType;
import org.mtravis.microservices.model.Vehicle;

@Data
@Builder
public class ParkingAllocationInformation {
    @JsonProperty("parking_spot")
    public long parkingSpot;
    @JsonProperty("vehicle_size")
    public ParkingSpotType spotType;
}

