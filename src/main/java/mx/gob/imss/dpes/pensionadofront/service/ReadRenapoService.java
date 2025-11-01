/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.pensionadofront.service;

import java.util.logging.Level;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import mx.gob.imss.dpes.common.exception.AlternateFlowException;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.pensionado.model.Pensionado;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpIn;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpOut;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpRequest;
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaIncidenciaTitularGrupoResponse;
import mx.gob.imss.dpes.interfaces.sistrap.model.PensionadoRequest;
import mx.gob.imss.dpes.pensionadofront.exception.PensionadoException;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoVigencia;
import mx.gob.imss.dpes.pensionadofront.restclient.PensionadoClient;
import mx.gob.imss.dpes.pensionadofront.restclient.RenapoClient;
import mx.gob.imss.dpes.pensionadofront.restclient.SistrapClient;

/**
 *
 * @author salvador.pocteco
 */
@Provider
public class ReadRenapoService extends ServiceDefinition<PensionadoVigencia, PensionadoVigencia>{
	

	@Inject
	@RestClient
	private RenapoClient renapoClient;

    @Override
    public Message<PensionadoVigencia> execute(Message<PensionadoVigencia> request) throws BusinessException {
       
    
    	RenapoCurpIn renapoCurpIn = new RenapoCurpIn();
    	renapoCurpIn.setCurp(request.getPayload().getRequest().getPensionado().getCurp());
        
        log.log(Level.INFO, "Servicio de consulta de vigencia que va al servicio de renapo {0}", renapoCurpIn); 
        Response load = renapoClient.load( renapoCurpIn );        
      
        if (load.getStatus() == 200) {
        	RenapoCurpOut response= load.readEntity(RenapoCurpOut.class);
        	request.getPayload().getRequest().setRenapoCurpOut(response);
        	return response(load, request);
        }
        throw new PensionadoException(PensionadoException.MSG009);  
      }
      


    
}
