/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import exception.EmailJaCadastradoException;
import exception.LoginJaCadastradoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.ContaModel;
import model.TransacaoModel;
import model.UsuarioModel;
import rest.ContaRESTClient;
import rest.TransacaoRESTClient;
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
    
    public UsuarioModel getUsuarioPorLoginSenha(UsuarioModel u) {
        UsuarioRESTClient rest = new UsuarioRESTClient();
        return rest.findUserByLoginAndPassword(u.getLogin(), u.getSenha());
    }
    
    public void adicionarConta(ContaModel c) {
        ContaRESTClient rest = new ContaRESTClient();
        rest.create(c);
    }
    
    public void adicionarTransacao(TransacaoModel t) {
        TransacaoRESTClient rest = new TransacaoRESTClient();
        t.setDataTransacao(getDataAtual());
        rest.create(t);
    }
    
    public void adicionarUsuario(UsuarioModel u) throws EmailJaCadastradoException, LoginJaCadastradoException {
        UsuarioRESTClient rest = new UsuarioRESTClient();
        if (rest.findUserByEmail(u.getEmail()) != null) {
            throw new EmailJaCadastradoException("");
        }
        if (rest.findUserByLogin(u.getLogin()) != null) {
            throw new LoginJaCadastradoException("");
        }
        rest.create(u);
    }
    
    public ContaModel findContaById(ContaModel c) {
        ContaRESTClient rest = new ContaRESTClient();
        return rest.find(Long.parseLong(String.valueOf(c.getId())));
    }
    
    public String getDataAtual() {        
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date(System.currentTimeMillis());
              
        return sd.format(dataAtual);
    }
    
    public List<ContaModel> getContasPorUsuario(UsuarioModel u) {
        ContaRESTClient rest = new ContaRESTClient();
        return rest.findAccountByUser(Integer.parseInt(u.getId()));
    }
    
    public List<TransacaoModel> getTransacoesPorUsuario(UsuarioModel u) {
        TransacaoRESTClient rest = new TransacaoRESTClient();
        List<TransacaoModel> transacoes = rest.findTransactionByUser(Integer.parseInt(u.getId()));
        
        return transacoes;
    }
    
    public List<TransacaoModel> getTransacoesPorConta(ContaModel c) {
        TransacaoRESTClient rest = new TransacaoRESTClient();
        List<TransacaoModel> transacoes = rest.findTransactionByAccount(Integer.parseInt(c.getId()));
        
        return transacoes;
    }
    
    public void apagarTransacoesPorContaId(ContaModel c) {
        TransacaoRESTClient rest = new TransacaoRESTClient();
        for (TransacaoModel t : getTransacoesPorConta(c)) {
            rest.delete(Long.parseLong(t.getId()));
        }
    }
}