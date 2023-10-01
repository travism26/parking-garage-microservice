package org.mtravis.microservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class TicketDto {
    @JsonProperty("ticket_id")
    public UUID ticketId; // this will be set by the other microservice
    @JsonProperty("parking_spot")
    public long parkingSpot; // this will be set by the other microservice
    @JsonProperty("spot_type")
    public Vehicle.VehicleType spotType;
    @JsonProperty("entry_time")
    public Instant entryTime;
    public Instant exitTime;

    public void generateId(){
        this.ticketId = UUID.randomUUID();
    }
}