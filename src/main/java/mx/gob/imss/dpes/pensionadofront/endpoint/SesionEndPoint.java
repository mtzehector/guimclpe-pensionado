/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.pensionadofront.endpoint;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.pensionadofront.restclient.SesionClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author Diego Velazquez
 */

@Path("/obtenerSesion")
@RequestScoped
public class SesionEndPoint  extends BaseGUIEndPoint<BaseModel, BaseModel, BaseModel>{
    
    @Inject
    @RestClient
    SesionClient client;
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSesion(){
        Response load = client.load();
        
        if(load.getStatus()==200){
            return load;
        }
        return toResponse( new Message( new RecursoNoExistenteException() ));
    }
    
}
