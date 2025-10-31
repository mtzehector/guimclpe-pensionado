package mx.gob.imss.dpes.pensionadofront.restclient;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.interfaces.persona.model.PersonaModel;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author luisr.rodriguez
 */
@Path("/pensionado")
@RegisterRestClient
public interface PersonaPensionadoClient {
    @PUT
    @Path("/actualizarNombre")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateDatos(PersonaModel persona);
}
