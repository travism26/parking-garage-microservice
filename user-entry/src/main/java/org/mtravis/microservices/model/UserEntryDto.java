package org.mtravis.microservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * This class represents a DTO of an outgoing userEntry
 */
@Data
@Builder
public class UserEntryDto {
    public UUID id; // auto generated VIA this service
    @JsonProperty("ticket_id")
    public UUID ticketId; // this will be set by the other microservice

    public Vehicle vehicle; // required for the parking service / spot allocation vehicle.type
    @JsonProperty("parking_spot")
    public long parkingSpot; // this will be set by the other microservice

    public void generateId(){
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "UserEntryDto{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", vehicle=" + vehicle +
                '}';
    }
}
