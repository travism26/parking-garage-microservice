package org.mtravis.microservices.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingSpotDto {

    @JsonProperty("parking_spot")
    public long parkingSpot;
    @JsonProperty("parking_type")
    public ParkingSpot.spotType spotType;
}
