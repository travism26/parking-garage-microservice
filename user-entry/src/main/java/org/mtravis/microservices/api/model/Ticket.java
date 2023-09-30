package org.mtravis.microservices.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Ticket {
    @JsonProperty("ticket_id")
    public UUID id;
    @JsonProperty("parking_spot_number")
    public long parkingSpot;
    public String licenseNumber;
    @JsonProperty("entry_time")
    public Instant entryTime;
    @JsonProperty("exit_time")
    public Instant exitTime;
}
