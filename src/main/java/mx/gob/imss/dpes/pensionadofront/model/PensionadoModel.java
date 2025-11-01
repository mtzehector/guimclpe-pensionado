package mx.gob.imss.dpes.pensionadofront.model;

import lombok.Data;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.persona.model.PersonaModel;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpRequest;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoResponse;
import mx.gob.imss.dpes.interfaces.sistrap.model.Pensionado;

/**
 * Modelo para contener Pensionado Renapo, Sistrap y Persona SIPRE.
 * @author luisr.rodriguez
 */
@Data
public class PensionadoModel extends BaseModel{
    private String curp;
    private String nss;
    private Long sesion;
    private RenapoResponse renapo;
    private RenapoCurpRequest renapoRequest;
    private Pensionado pensionado;
    private PersonaModel persona;
    private Boolean flagError;
    private String idGrupoFamiliar;
}
