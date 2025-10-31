package mx.gob.imss.dpes.pensionadofront.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.persona.model.PersonaModel;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoModel;
import mx.gob.imss.dpes.pensionadofront.restclient.PersonaClient;
import mx.gob.imss.dpes.pensionadofront.rule.ComparaRenapoVsSipre;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author luisr.rodriguez
 */
@Provider
public class PersonaService extends ServiceDefinition<PensionadoModel,PensionadoModel>{
    @Inject
    @RestClient
    PersonaClient client;
    @Inject
    private ComparaRenapoVsSipre rule1;

    @Override
    public Message<PensionadoModel> execute(Message<PensionadoModel> request) throws BusinessException {
        log.log(Level.INFO, ">>>promotorfront|PersonaService: {0}", request.getPayload());

        Response load = client.load(request.getPayload().getPensionado().getCveCurp());
        try {
            if (load.getStatus() == 200) {
                PersonaModel res = load.readEntity(PersonaModel.class);
                log.log(Level.INFO, ">>>pensionadoFront|PersonaService|Response: {0}", res);
                if (res.getCorreoElectronico() != null) {
                    request.getPayload().setPersona(res);
                    rule1.apply(request.getPayload());
                    return request;
                } else {
                    log.log(Level.INFO, ">>>pensionadoFront|PersonaService|ResponseNotFound");
                    PersonaModel p = new PersonaModel();
                    p.setCorreoElectronico("sinregistro");
                    request.getPayload().setPersona(p);
                    return request;
                }

            } else {
                log.log(Level.INFO, ">>>pensionadoFront|PersonaService|ResponseNotFound");
                PersonaModel p = new PersonaModel();
                p.setCorreoElectronico("sinregistro");
                request.getPayload().setPersona(p);
                return request;
            }
        } catch (Exception e) {
            log.log(Level.INFO, ">>>pensionadoFront|PersonaService|ERRORResponse: {0}", e);
        }
        return response(null, ServiceStatusEnum.EXCEPCION, null, null);
    
    }
    
}
