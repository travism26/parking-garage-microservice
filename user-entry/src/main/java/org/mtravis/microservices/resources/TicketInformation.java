package org.mtravis.microservices.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class TicketInformation {

    @JsonProperty("ticket_id")
    public UUID ticketId;
    @JsonProperty("parking_spot")
    public long parkingSpot;
    @JsonProperty("entry_time")
    public Instant entryTime;

    @Override
    public String toString() {
        return "TicketInformation{" +
                "ticketId=" + ticketId +
                ", parkingSpot=" + parkingSpot +
                ", entryTime=" + entryTime +
                '}';
    }
}
