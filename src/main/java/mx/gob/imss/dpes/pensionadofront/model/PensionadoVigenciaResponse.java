package mx.gob.imss.dpes.pensionadofront.model;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;

public class PensionadoVigenciaResponse extends BaseModel {
	 @Getter @Setter private String idIncidenciaSistrap;
	 @Getter @Setter private String desEstatusCURP;
	 @Getter @Setter private Boolean vigente;
	 
}
