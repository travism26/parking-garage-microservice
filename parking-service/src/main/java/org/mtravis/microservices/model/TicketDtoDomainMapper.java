package org.mtravis.microservices.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta")
public interface TicketDtoDomainMapper {

    @Mapping(source = "ticketId", target = "id")
    Ticket toDomain(TicketDto ticketDto);

    @Mapping(source = "id", target = "ticketId")
    TicketDto toDto(Ticket ticket);
}
