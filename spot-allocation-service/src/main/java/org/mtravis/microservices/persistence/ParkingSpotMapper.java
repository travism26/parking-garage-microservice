package org.mtravis.microservices.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mtravis.microservices.model.ParkingSpot;

import java.io.IOException;

public class ParkingSpotMapper implements DomainEntityMapper<ParkingSpot, ParkingSpotEntity> {
    @Override
    public ParkingSpotEntity toEntity(ParkingSpot domain) throws JsonProcessingException {
        ParkingSpotEntity entity = new ParkingSpotEntity();
        entity.id = domain.getId();
        entity.spotNumber = domain.getSpotNumber();
        entity.spotType = domain.getSpotType();
        entity.occupied = domain.isOccupied();
        return entity;
    }

    @Override
    public ParkingSpot toDomain(ParkingSpotEntity entity) throws IOException {
        return ParkingSpot.builder()
                .id(entity.id)
                .spotNumber(entity.spotNumber)
                .spotType(entity.spotType)
                .occupied(entity.occupied)
                .build();
    }
}
