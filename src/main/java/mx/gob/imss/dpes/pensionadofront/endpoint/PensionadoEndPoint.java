package mx.gob.imss.dpes.pensionadofront.endpoint;

import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import org.eclipse.microprofile.openapi.annotations.Operation; 
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.pensionado.model.Pensionado;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoModel;

import mx.gob.imss.dpes.pensionadofront.service.PensionadoService;
import mx.gob.imss.dpes.pensionadofront.service.PensionadoSistrap;
import mx.gob.imss.dpes.pensionadofront.service.PersonaService;
import mx.gob.imss.dpes.pensionadofront.service.RenapoService;


/**
 *
 * @author cesar.leon
 */
@Path("/pensionado")
@RequestScoped
public class PensionadoEndPoint  extends BaseGUIEndPoint<Pensionado, Pensionado, Pensionado>{
    @Inject
    PensionadoService pensionadoService;
    @Inject
    RenapoService renapo;
    @Inject
    PensionadoSistrap pensionadoSistrap;
    @Inject
    PersonaService persona;
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener los datos de un pensionado",
            description = "Obtener los datos de un pensionado")
    @Override
    public Response load(Pensionado request)  throws BusinessException {
       
           ServiceDefinition[] steps = { pensionadoService };            
        Message<Pensionado> response
                = pensionadoService.executeSteps(steps, new Message<>(request) );

        return toResponse(response);    
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validar")
    public Response validarPensionado(PensionadoModel request) throws BusinessException{
        log.log(Level.INFO, ">>>pensionadoFront|PensionadoEndPoint|validarPensionado: {0}", request);
        ServiceDefinition[] steps = {renapo,
            pensionadoSistrap,
            persona};
        Message<PensionadoModel> response = renapo.executeSteps(steps, new Message<>(request));
        if (Message.isException(response)) {
            log.log(Level.INFO, "Response catch: {0}", response);
            return toResponse(response);
        }
        return toResponse(response);
    }
   
}
