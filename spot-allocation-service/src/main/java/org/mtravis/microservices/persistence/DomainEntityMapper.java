package org.mtravis.microservices.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface DomainEntityMapper<D, E> {
    /**
     * Map {@code D} object to {@code E} object
     * @param domain the domain object to map from.
     * @return The {@code E} object
     * @throws JsonProcessingException
     */
    E toEntity(D domain) throws JsonProcessingException;

    /**
     * Map {@code E} object to {@code D} object.
     * @param entity the entity object to map from
     * @return the {@code D} object.
     * @throws IOException
     */
    D toDomain(E entity) throws IOException;
}