/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author roseanealves
 */
public class UsuarioJaExisteException extends Exception {
    
    public UsuarioJaExisteException() {
        super();
    }
    
    public UsuarioJaExisteException(String message) {
        super(message);
    }
}
