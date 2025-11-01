package mx.gob.imss.dpes.pensionadofront.rule;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.rule.BaseRule;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoVigenciaRequest;

public class RN028Rule extends BaseRule<RN028Rule.Input, RN028Rule.Output> {
	
	static final String INCIDENCIA_VIGENCTE_SISTRAP = "VG";
	static final String ESTADO_VIVO_RENAPO = "1";

  @Override
  public RN028Rule.Output apply(Input input) {
	  	boolean incidenciaSistrap= input.incidenciaSistrap.equals(INCIDENCIA_VIGENCTE_SISTRAP);
	  	boolean estadoVitalRenapo= input.estadoVitalRenapo.equals(ESTADO_VIVO_RENAPO);
	  	return new Output(incidenciaSistrap && estadoVitalRenapo);
    }



  public class Input extends BaseModel {
		protected String incidenciaSistrap;
		protected String estadoVitalRenapo;

  public Input(String incidenciaSistrap, String estadoVitalRenapo) {
      this.incidenciaSistrap = incidenciaSistrap;
      this.estadoVitalRenapo = estadoVitalRenapo;
    }

  }

  public class Output extends BaseModel {
	@Getter private Boolean vigente;

    public Output(Boolean vigente) {
      this.vigente = vigente;
    }


  }
}
