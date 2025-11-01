package mx.gob.imss.dpes.pensionadofront.restclient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.interfaces.bitacora.model.Bitacora;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author luisr.rodriguez
 */
@Path("/bitacora")
@RegisterRestClient
public interface BitacoraBackClient {
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(Bitacora request);  
}
