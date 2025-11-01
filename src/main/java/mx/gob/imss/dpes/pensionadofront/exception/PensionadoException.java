/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.pensionadofront.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;


/**
 *
 * @author antonio
 */
public class PensionadoException extends BusinessException{
  public final static String MSG009 = "msg009";
  private final static String KEY = "msg058";
  
  public PensionadoException() {
    super(KEY);
  }
  
  public PensionadoException(String key) {
	    super(key);
	  }
}
