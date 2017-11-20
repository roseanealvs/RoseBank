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
public class LoginJaCadastradoException extends Exception {
    public LoginJaCadastradoException(String message) {
        super(message);
    }
    
    public LoginJaCadastradoException(Throwable cause) {
        super(cause);
    }
}
