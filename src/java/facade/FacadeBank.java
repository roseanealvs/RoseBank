/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import Exceptions.EmailJaExisteException;
import Exceptions.UsuarioJaExisteException;
import dao.ContaDao;
import dao.DaoFactory;
import dao.TransacaoDao;
import dao.UsuarioDao;
import java.util.List;
import model.Conta;
import model.Transacao;
import model.Usuario;

/**
 *
 * @author roseanealves
 */
public class FacadeBank {
    public boolean logar(Usuario user) {
        UsuarioDao userDao = DaoFactory.getUsuarioDaoInstance();
        return userDao.getLoginSenhaValido(user.getLogin(), user.getSenha());
    }
    
    public Usuario getUsuario(Usuario user) {
        UsuarioDao userDao = DaoFactory.getUsuarioDaoInstance();
        return userDao.getUsuario(user.getLogin());
    }
    
    public Conta getConta(String id) {
        ContaDao contaDao = DaoFactory.getContaDaoInstance();
        return contaDao.getContaPorID(id);
    }
    
    public void update(Usuario user) {
        UsuarioDao userDao = DaoFactory.getUsuarioDaoInstance();
        userDao.updateUsuario(user);
    }
    
    public void deleteUsuario(Usuario user) {
        UsuarioDao userDao = DaoFactory.getUsuarioDaoInstance();
        userDao.deleteUsuario(user);
    }
    
    
    public List<Conta> getContasPorUsuario(String idUsuario){
        ContaDao contaDao = DaoFactory.getContaDaoInstance();
        return contaDao.getContas(idUsuario);
    }
    
    public List<Usuario> getUsuarios(String excetoUserLogin) {
        UsuarioDao userDao = DaoFactory.getUsuarioDaoInstance();
        return userDao.getUsuarios(excetoUserLogin);
    }
    
    public void deleteConta(String id) {
        ContaDao contaDao = DaoFactory.getContaDaoInstance();
        contaDao.deleteConta(id);
    }
    
    public void updateConta(Conta c) {
        ContaDao contaDao = DaoFactory.getContaDaoInstance();
        contaDao.updateConta(c.getId(), c.getDescricao());
    }
    
    public List<Usuario> getAmigosUsuario(String id) {
        UsuarioDao userDao = DaoFactory.getUsuarioDaoInstance();
        return userDao.getAmigosUsuario(id);
    }
    
    public void updateValorConta(Conta c) throws Exception {
        ContaDao contaDao = DaoFactory.getContaDaoInstance();
        if (Double.parseDouble(this.getConta(c.getId()).getValorAtual()) < Double.parseDouble(c.getValorTransferir())) {
            throw new Exception("Saldo Insuficiente");
        }
        contaDao.updateValorConta(c.getId(), c.getValorTransferir());
    }
    
    
    public void insertConta(Conta c) {
        ContaDao contaDao = DaoFactory.getContaDaoInstance();
        contaDao.insertConta(c.getIdUsuario(), c.getDescricao());
    }
    
    public List<Transacao> getTransacoes(String usuarioLogin) {
        TransacaoDao transacaoDao = DaoFactory.getTransacaoDaoInstance();
        return transacaoDao.getTransacoes(usuarioLogin);
    }
    
    public void adicionarAmigo() {
        
    }
    
    public void inserirUsuario(Usuario user) throws UsuarioJaExisteException, EmailJaExisteException {
        UsuarioDao userDao = DaoFactory.getUsuarioDaoInstance();
        if (userDao.getUsuario(user.getLogin()) != null) {
            throw new UsuarioJaExisteException("Login já cadastrado");
        }
        if (userDao.isEmailCadastrado(user.getEmail())) {
            throw new EmailJaExisteException("Email já cadastrado");
        }
        userDao.insertUsuario(user);
    }
}
