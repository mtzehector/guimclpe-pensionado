package mx.gob.imss.dpes.pensionadofront.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RenapoVsSistrapNoMacthingException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sistrap.model.Pensionado;
import mx.gob.imss.dpes.interfaces.sistrap.model.PensionadoRequest;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoModel;
import mx.gob.imss.dpes.pensionadofront.restclient.PensionadoSistrapClient;
import mx.gob.imss.dpes.pensionadofront.rule.CompararRenapoVsSistrap;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Servicio de consulta pensionado Sistrap
 * @author luisr.rodriguez
 */
@Provider
public class PensionadoSistrap extends ServiceDefinition<PensionadoModel,PensionadoModel> {
    @Inject
    @RestClient
    PensionadoSistrapClient client;
    @Inject
    CompararRenapoVsSistrap rule1;

    @Override
    public Message<PensionadoModel> execute(Message<PensionadoModel> request) 
            throws BusinessException {
        log.log(Level.INFO,">>>pensionadoFront|PensionadoSistrap: {0}", request.getPayload());
        PensionadoRequest pr = new PensionadoRequest();
        pr.setIdNss(request.getPayload().getNss());
        pr.setIdGrupoFamiliar(request.getPayload().getIdGrupoFamiliar());
        Response load = client.load(pr);
        
        if(load.getStatus() == 200){
            Pensionado response = 
                    load.readEntity(Pensionado.class);
            request.getPayload().setPensionado(response);
            rule1.apply(request.getPayload());
            
            if(request.getPayload().getFlagError()){
               return response(null, ServiceStatusEnum.EXCEPCION, new RenapoVsSistrapNoMacthingException(), null);
            }
            
            return new Message<>(request.getPayload());
        }
        
        return response(request.getPayload(),ServiceStatusEnum.EXCEPCION,null,null);
    }
    
}
