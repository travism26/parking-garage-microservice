package org.mtravis.microservices.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ParkingSpot {
    public UUID id;
    public long spotNumber;
    public spotType spotType;

    public static enum spotType {
        SMALL, MEDIUM, LARGE, XLARGE;
    }
}
