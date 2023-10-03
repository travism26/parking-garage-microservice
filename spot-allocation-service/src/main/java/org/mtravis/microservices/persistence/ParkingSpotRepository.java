package org.mtravis.microservices.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParkingSpotRepository implements PanacheRepositoryBase<ParkingSpotEntity, Integer> {
}
