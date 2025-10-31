package mx.gob.imss.dpes.pensionadofront.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpIn;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpRequest;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoModel;
import mx.gob.imss.dpes.pensionadofront.restclient.RenapoClient;
import mx.gob.imss.dpes.renapo.exception.RenapoCurpException;
import mx.gob.imss.dpes.renapo.service.RenapoCurpService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Servicio de consulta a RENAPO
 * @author luisr.rodriguez
 */
@Provider
public class RenapoService extends ServiceDefinition<PensionadoModel,PensionadoModel> {
    @Inject
    @RestClient
    private RenapoClient client;
    @Inject
    RenapoCurpService renapo;

    @Override
    public Message<PensionadoModel> execute(Message<PensionadoModel> request) throws BusinessException {
        log.log(Level.INFO,">>>pensionadofront|RenapoService: {0}", request.getPayload());
        RenapoCurpRequest renapoRequest = new RenapoCurpRequest();
        RenapoCurpIn in = new RenapoCurpIn();
        in.setCurp(request.getPayload().getCurp());
        renapoRequest.setRenapoCurpIn(in);
        
        Message<RenapoCurpRequest> response = renapo.execute(new Message<>(renapoRequest));
        log.log(Level.INFO, ">>>>  ObtenDatosRenapo response= {0}", response);
        
        if (Message.isExito(response)) {
            request.getPayload().setRenapoRequest(response.getPayload());
            return request;
        }
        
        log.log(Level.SEVERE, "!!!ERROR >>>>ObtenDatosRenapo Request: {0}", request.getPayload());
        throw new RenapoCurpException();
    }
    
}
