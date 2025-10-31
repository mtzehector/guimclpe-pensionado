package mx.gob.imss.dpes.pensionadofront.service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.model.Message;

import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.pensionado.model.Pensionado;
import mx.gob.imss.dpes.pensionadofront.exception.PensionadoException;
import mx.gob.imss.dpes.pensionadofront.restclient.PensionadoClient;




@Provider
public class PensionadoService extends ServiceDefinition<Pensionado, Pensionado>{

  
  @Inject
  @RestClient
  private PensionadoClient pensionadoClient;
  
  
  @Override
  public Message<Pensionado> execute(Message<Pensionado> request) {
        
    Response load = pensionadoClient.load(request.getPayload());
    if( load.getStatus() == 200 ){
        
      Pensionado pensionadoResponse = load.readEntity(Pensionado.class);
      
       return new Message<>(pensionadoResponse); 
    }
        
    return response(null, ServiceStatusEnum.EXCEPCION, new PensionadoException(), null );
  }
  
}
