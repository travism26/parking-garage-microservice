package org.mtravis.microservices.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TicketDto {
    @JsonProperty("ticket_id")
    public UUID ticketId; // this will be set by the other microservice
    //    User user; // save some user data maybe?
    public Vehicle vehicle; // This is sent from user-entry service
    @JsonProperty("parking_spot")
    public long parkingSpot; // this will be set by the other microservice

    public void generateId(){
        this.ticketId = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "ticketId=" + ticketId +
                ", vehicle=" + vehicle +
                ", parkingSpot=" + parkingSpot +
                '}';
    }
}