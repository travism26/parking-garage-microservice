package org.mtravis.microservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.mtravis.microservices.persistence.ParkingSpotType;

@Data
@Builder
public class ParkingSpotDto {

    @JsonProperty("parking_spot")
    public long parkingSpot;
    @JsonProperty("vehicle_size")
    public ParkingSpotType spotType;
}
