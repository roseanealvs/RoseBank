/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author roseanealves
 */
public class EmailJaCadastradoException extends Exception {
    
    public EmailJaCadastradoException(String message) {
        super(message);
    }
    
    public EmailJaCadastradoException(Throwable cause) {
        super(cause);
    }
}
