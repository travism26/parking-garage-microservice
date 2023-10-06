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
    public UUID id;
    @JsonProperty("ticket_id")
    public UUID ticketId;
    public Vehicle vehicle;
    @JsonProperty("parking_spot")
    public long parkingSpot;

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
