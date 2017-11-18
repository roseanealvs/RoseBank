/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import model.ContaModel;
import model.UsuarioModel;
import rest.ContaRESTClient;
import rest.UsuarioRESTClient;

/**
 *
 * @author roseanealves
 */
public class FacadeBankREST {
    public void editarConta(ContaModel c) {
        ContaRESTClient rest = new ContaRESTClient();
        rest.edit(c);
    }
    
    public void editarUsuario(UsuarioModel u) {
        UsuarioRESTClient rest = new UsuarioRESTClient();
        rest.edit(u);
    }
    
    public void deletarConta(ContaModel c) {
        ContaRESTClient rest = new ContaRESTClient();
        rest.delete(c.getId());
    }
    
    public void deletarUsuario(UsuarioModel u) {
        UsuarioRESTClient rest = new UsuarioRESTClient();
        rest.delete(u.getId());
    }
}
