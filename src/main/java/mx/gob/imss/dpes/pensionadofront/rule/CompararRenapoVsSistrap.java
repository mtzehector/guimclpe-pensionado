package mx.gob.imss.dpes.pensionadofront.rule;

import java.util.logging.Level;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.rule.BaseRule;
import mx.gob.imss.dpes.pensionadofront.model.PensionadoModel;

/**
 * Evaluador de nombre RENAPO y SISTRAP
 * @author luisr.rodriguez
 */
@Provider
public class CompararRenapoVsSistrap extends BaseRule<PensionadoModel,PensionadoModel> {

    @Override
    public PensionadoModel apply(PensionadoModel request) throws BusinessException {
        //Comparacion NOMBRE
        /*if(request.getPensionado().getNomNombre().contains("?")){
            String nuevoNombre = request.getPensionado().getNomNombre().replace("?", "Ñ");
            request.getPensionado().setNomNombre(nuevoNombre);
        }*/
        request.getPensionado().setNomNombre(remplazaCaracteres(request.getPensionado().getNomNombre()));
        if(request.getRenapoRequest().getRenapoCurpOut().getNombres().length()>40){
            if (!request.getRenapoRequest().getRenapoCurpOut().getNombres().substring(0, 40).equals(request.getPensionado().getNomNombre())) {
            log.log(Level.SEVERE, ">>> Exception CompararRenapoVsSistrap Nombres");
            request.setFlagError(Boolean.TRUE);
            return request;
            }else{
                request.getPensionado().setNomNombre(request.getRenapoRequest().getRenapoCurpOut().getNombres());
            }
        }else{
            if (!request.getRenapoRequest().getRenapoCurpOut().getNombres().equals(request.getPensionado().getNomNombre())) {
            log.log(Level.SEVERE, ">>> Exception CompararRenapoVsSistrap Nombres");
            request.setFlagError(Boolean.TRUE);
            return request;
            }else{
                request.getPensionado().setNomNombre(request.getRenapoRequest().getRenapoCurpOut().getNombres());
            }
        }
        
        //Comparacion APELLIDO PATERNO
        request.getPensionado().setNomApellidoPaterno(remplazaCaracteres(request.getPensionado().getNomApellidoPaterno()));
        if (!request.getRenapoRequest().getRenapoCurpOut().getApellido1().equals(request.getPensionado().getNomApellidoPaterno())) {
            log.log(Level.SEVERE, ">>> Exception CompararRenapoVsSistrap ApellidoPaterno");
            request.setFlagError(Boolean.TRUE);
            return request;
        }
        
        //Comparacion APELLIDO MATERNO
        request.getPensionado().setNomApellidoMaterno(remplazaCaracteres(request.getPensionado().getNomApellidoMaterno()));
        if (request.getRenapoRequest().getRenapoCurpOut().getApellido2() != null && !request.getRenapoRequest().getRenapoCurpOut().getApellido2().equals("")) {
            if (!request.getRenapoRequest().getRenapoCurpOut().getApellido2().equals(request.getPensionado().getNomApellidoMaterno())) {
                log.log(Level.SEVERE, ">>> Exception CompararRenapoVsSistrap ApellidoMaterno");
                request.setFlagError(Boolean.TRUE);
                return request;
            }
        } else {
            if (request.getPensionado().getNomApellidoMaterno() != null) {
                log.log(Level.SEVERE, ">>> Exception CompararRenapoVsSistrap SIN ApellidoMaterno");
                request.setFlagError(Boolean.TRUE);
                return request;
            }
        }
        request.setFlagError(Boolean.FALSE);
        
        return request;
    }
    
    private String remplazaCaracteres(String nombre){
        if(nombre!= null){
            if (nombre.contains("?")){
                nombre = nombre.replace("?", "Ñ");
            }
            if (nombre.contains("#")){
                nombre = nombre.replace("#", "Ñ");
            }
        }
        return nombre;
    }
    
}
