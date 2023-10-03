package org.mtravis.microservices.persistence;

import jakarta.persistence.*;
import org.mtravis.microservices.model.ParkingSpot;

import java.util.UUID;

/**
 * This will be the database representation of a Parking Spot.
 */
@Entity
@Table(name = "parking_spot")
public class ParkingSpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @Column(name = "id", nullable = false)
    public UUID id;
    @Column(name = "spot_number", nullable = false)
    public long spotNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "spot_type", nullable = false)
    public ParkingSpotType spotType;
    @Column(name = "occupied", nullable = false)
    public boolean occupied;

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "ParkingSpotEntity{" +
                "pk=" + pk +
                ", id=" + id +
                ", spotNumber=" + spotNumber +
                ", spotType=" + spotType +
                ", occupied=" + occupied +
                '}';
    }
}
