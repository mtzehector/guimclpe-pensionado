/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.pensionadofront.endpoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.pensionado.model.Pensionado;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoVigencia;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoVigenciaResponse;
import mx.gob.imss.dpes.pensionadofront.service.ReadRenapoService;
import mx.gob.imss.dpes.pensionadofront.service.ReadVigenciaSistrapService;
import mx.gob.imss.dpes.pensionadofront.service.ValidarVigenciaService;
import org.eclipse.microprofile.openapi.annotations.Operation;

/**
 *
 * @author salvador.pocteco
 */
@Path("/validarVigencia")
@RequestScoped
public class ValidarVigenciaEndPoint extends BaseGUIEndPoint<BaseModel, PensionadoVigencia, BaseModel> {

	@Inject
    ReadVigenciaSistrapService readVigenciaSistrapService;
	
	@Inject
    ReadRenapoService readRenapoService;
	
	@Inject
    ValidarVigenciaService validarVigenciaService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validar la vigencia",
            description = "Validar la vigencia")
    @Override
    public Response load(PensionadoVigencia request) throws BusinessException {
    	 ServiceDefinition[] steps = { readVigenciaSistrapService,readRenapoService,validarVigenciaService };   
        Message<PensionadoVigencia> response = readVigenciaSistrapService.execute(new Message<>(request));

        return toResponse(new Message<PensionadoVigenciaResponse>(response.getPayload().getResponse()));
    }
}
