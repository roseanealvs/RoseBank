/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import model.Conta;
import model.Usuario;
import rest.ContaRESTClient;
import rest.UsuarioRESTClient;

/**
 *
 * @author roseanealves
 */
public class FacadeBankREST {
    public void editarConta(Conta c) {
        ContaRESTClient rest = new ContaRESTClient();
        rest.edit(c);
    }
    
    public void editarUsuario(Usuario u) {
        UsuarioRESTClient rest = new UsuarioRESTClient();
        rest.edit(u);
    }
}
