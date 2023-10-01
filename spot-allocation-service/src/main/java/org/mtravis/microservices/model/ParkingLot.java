package org.mtravis.microservices.model;

import java.util.List;
import java.util.UUID;

/**
 * This class will represent the relationship between the parking lot and the parking spots
 * This will be some form of grouping parking spots to a particular area.
 */
public class ParkingLot {
    public UUID id;
    public List<ParkingSpot> parkingSpots;
}
