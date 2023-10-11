package org.mtravis.microservices.persistence;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/**
 * This will be the database representation of a Ticket.
 */

@Entity
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @Column(name = "id", nullable = false)
    public UUID id;
    @Column(name = "parking_spot", nullable = false)
    public long parkingSpot;
    @Column(name = "license_number", nullable = false)
    public String licenseNumber;
    @Column(name = "entry_time", columnDefinition = "TIMESTAMP", nullable = false)
    public Instant entryTime;
    @Column(name = "exit_time", columnDefinition = "TIMESTAMP", nullable = true)
    public Instant exitTime;

    public void setPk(Integer pk) {
        this.pk = pk;
    }
}
