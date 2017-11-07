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
public class EmailJaExisteException extends Exception {
    
    public EmailJaExisteException() {
        super();
    }
    
    public EmailJaExisteException(String message) {
        super(message);
    }
}
