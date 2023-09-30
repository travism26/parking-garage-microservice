package org.mtravis.microservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Produces;
import org.mtravis.microservices.model.Ticket;
import org.mtravis.microservices.persistence.TicketEntity;
import org.mtravis.microservices.persistence.TicketMapper;
import org.mtravis.microservices.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *  This is the business logic related to Ticket
 */

@Produces
@ApplicationScoped
public class TicketService {
    @Inject
    TicketRepository ticketRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);
    private final TicketMapper ticketMapper = new TicketMapper();

    @Transactional
    public boolean create(Ticket ticket) throws JsonProcessingException {
        UUID id = ticket.getId();
        LOGGER.info("Attempting to persist a ticket with an ID '{}'", id);

        if (ticketRepository.find("id = ?1", id).firstResult() != null) {
            LOGGER.warn("Ticket already exists in the database, did not persist");
            return false;
        }
        TicketEntity ticketEntity = ticketMapper.toEntity(ticket);
        LOGGER.info("Saving to DB");
        ticketRepository.persist(ticketEntity);
        LOGGER.info("Successfully persisted book: {}", ticket);
        return true;
    }

    @Transactional
    public boolean delete(UUID ticketId){
        TicketEntity ticketEntity =
                ticketRepository.find("id = ?1", ticketId).firstResult();
        if (ticketEntity == null){
            LOGGER.info("Ticket you attempted to delete does not exist");
            return false;
        }
        ticketRepository.delete("id = ?1", ticketId);
        return true;
    }

    public Collection<Ticket> getAll() throws IOException {
        LOGGER.info("Fetching all tickets");
        List<TicketEntity> ticketEntityList = ticketRepository.listAll();
        LOGGER.info("Number of tickets: {} found in the database", ticketEntityList.size());
        List<Ticket> tickets = new ArrayList<>();
        for(TicketEntity ticketEntity : ticketEntityList) {
            tickets.add(ticketMapper.toDomain(ticketEntity));
        }
        return tickets;
    }

    public Ticket getById(UUID ticketId) throws IOException {
        TicketEntity ticketEntity = ticketRepository.find("id = ?1", ticketId).firstResult();
        if (ticketEntity == null){
            LOGGER.info("No ticket found with that id");
            return null;
        }
        LOGGER.info("Retrieved ticket with id={}", ticketId);
        return ticketMapper.toDomain(ticketEntity);
    }

}
