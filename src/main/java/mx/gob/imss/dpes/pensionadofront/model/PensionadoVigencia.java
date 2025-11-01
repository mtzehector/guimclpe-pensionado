package mx.gob.imss.dpes.pensionadofront.model;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;

public class PensionadoVigencia extends BaseModel {
		
	@Getter @Setter PensionadoVigenciaRequest request;
	@Getter @Setter PensionadoVigenciaResponse response;
	
}
