package mx.gob.imss.dpes.pensionadofront.model;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.pensionado.model.Pensionado;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpOut;
import mx.gob.imss.dpes.interfaces.sistrap.model.IncidenciaTitularGrupo;

public class PensionadoVigenciaRequest extends BaseModel {
	@Getter @Setter Pensionado pensionado;
	@Getter @Setter IncidenciaTitularGrupo incidencia;
	@Getter @Setter RenapoCurpOut renapoCurpOut;
	
}
