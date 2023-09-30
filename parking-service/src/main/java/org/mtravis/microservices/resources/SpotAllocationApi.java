package org.mtravis.microservices.api.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.mtravis.microservices.api.model.Vehicle;

@RegisterRestClient(configKey = "parking.allocation.services")
@ApplicationScoped
public interface SpotAllocationApi {

    @POST
    @Path("/api/spot/allocation")
    @Produces(MediaType.APPLICATION_JSON)
    ParkingAllocationInformation generateParkingTicketInfo(Vehicle vehicle);
}
