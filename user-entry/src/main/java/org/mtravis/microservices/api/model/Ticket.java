package org.mtravis.microservices.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Ticket {
    public UUID id;
    public long parkingSpot;
    public String licenseNumber;
    public Instant entryTime;
    public Instant exitTime;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", parkingSpot=" + parkingSpot +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                '}';
    }
}
