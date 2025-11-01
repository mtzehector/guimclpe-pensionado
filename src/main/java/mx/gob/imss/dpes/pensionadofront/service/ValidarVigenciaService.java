/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.pensionadofront.service;


import javax.ws.rs.ext.Provider;


import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.pensionadofront.exception.PensionadoException;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoVigencia;
import mx.gob.imss.dpes.pensionadofront.rule.RN028Rule;

/**
 *
 * @author salvador.pocteco
 */
@Provider
public class ValidarVigenciaService extends ServiceDefinition<PensionadoVigencia, PensionadoVigencia>{

	

    @Override
    public Message<PensionadoVigencia> execute(Message<PensionadoVigencia> request) throws BusinessException {
    	String incidenciaSistrap =request.getPayload().getRequest().getIncidencia().getIdIncidencia();
         
        String estadoVitalRenapo=request.getPayload().getRequest().getRenapoCurpOut().getStatusOper()!=null?
        		request.getPayload().getRequest().getRenapoCurpOut().getStatusOper():"1";
        RN028Rule r = new  RN028Rule();        
        RN028Rule.Output out=  r.apply(r.new Input(incidenciaSistrap,incidenciaSistrap));       
        if(!out.getVigente()) {
        	throw new PensionadoException(PensionadoException.MSG009); 
        }
        request.getPayload().getResponse().setIdIncidenciaSistrap(incidenciaSistrap);
        request.getPayload().getResponse().setDesEstatusCURP(estadoVitalRenapo);
        request.getPayload().getResponse().setVigente(out.getVigente());
        return request;
      }
      


    
}
