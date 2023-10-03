package org.mtravis.microservices.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.mtravis.microservices.persistence.ParkingSpotType;

import java.util.UUID;

@Data
@Builder
public class ParkingSpot {
    public UUID id;
    public long spotNumber;
    public ParkingSpotType spotType;
    public boolean occupied;

}
