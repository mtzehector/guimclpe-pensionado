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
import mx.gob.imss.dpes.interfaces.sistrap.model.ConsultaIncidenciaTitularGrupoResponse;
import mx.gob.imss.dpes.interfaces.sistrap.model.PensionadoRequest;
import mx.gob.imss.dpes.pensionadofront.exception.PensionadoException;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoVigencia;
import mx.gob.imss.dpes.pensionadofront.restclient.PensionadoClient;
import mx.gob.imss.dpes.pensionadofront.restclient.SistrapClient;
import mx.gob.imss.dpes.pensionadofront.rule.RN028Rule;

/**
 *
 * @author salvador.pocteco
 */
@Provider
public class ReadVigenciaSistrapService extends ServiceDefinition<PensionadoVigencia, PensionadoVigencia>{
	
	@Inject
	@RestClient
	private SistrapClient sistrapClient;
	

    @Override
    public Message<PensionadoVigencia> execute(Message<PensionadoVigencia> request) throws BusinessException {
       
    
        
        PensionadoRequest pr = new PensionadoRequest();
        pr.setIdGrupoFamiliar( request.getPayload().getRequest().getPensionado().getGrupoFamiliar() );
        pr.setIdNss( request.getPayload().getRequest().getPensionado().getNss() );
        
        log.log(Level.INFO, "Servicio de consulta de vigencia que va al servicio de sistrap {0}", pr); 
        Response load = sistrapClient.load( pr );
                      
        if (load.getStatus() == 200) {
        	ConsultaIncidenciaTitularGrupoResponse responseSistrap = load.readEntity(ConsultaIncidenciaTitularGrupoResponse.class);
        	request.getPayload().getRequest().setIncidencia(responseSistrap.getIncTitularGpo());	
        	return response(load, request);
        }
        throw new PensionadoException(PensionadoException.MSG009);  
      }
      

    
}
