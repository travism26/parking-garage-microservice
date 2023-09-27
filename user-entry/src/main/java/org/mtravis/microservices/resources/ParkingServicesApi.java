package org.mtravis.microservices.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.mtravis.microservices.api.model.Vehicle;

@RegisterRestClient(configKey = "parking.services")
@ApplicationScoped
public interface ParkingServicesApi {

    @POST
    @Path("/api/parking")
    @Produces(MediaType.APPLICATION_JSON)
    TicketInformation generateParkingTicketInfo(@QueryParam("license")String licence);
}
