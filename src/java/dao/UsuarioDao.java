/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Usuario;


/**
 *
 * @author roseanealves
 */
public class UsuarioDao {
    public boolean getLoginSenhaValido(String usuario, String senha) {
        boolean existe = false;
        int i = 1;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
            ResultSet rs;
            PreparedStatement ps;
            try {
                query.append("select login, senha from usuario where login = ? and senha = ?");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, usuario);
                ps.setString(i++, senha);
                rs = ps.executeQuery();
                if (rs.next()) {
                    existe = true;
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return existe;
    }
     public boolean isEmailCadastrado(String email) {
       
        int i = 1;
     
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
            ResultSet rs;
            PreparedStatement ps;
            try {
                query.append("select id from usuario where email = ?");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, email);
                
                rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    public Usuario getUsuario(String usuario) {
       
        int i = 1;
        Usuario user = null;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
            ResultSet rs;
            PreparedStatement ps;
            try {
                query.append("select id, login, nome, senha, email from usuario where login = ?");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, usuario);
                
                rs = ps.executeQuery();
                if (rs.next()) {
                    user = new Usuario();
                    user.setId(rs.getString("id"));
                    user.setLogin(rs.getString("login"));
                    user.setNome(rs.getString("nome"));
                    user.setSenha(rs.getString("senha"));
                    user.setEmail(rs.getString("email"));
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return user;
    }
    
    public void updateUsuario(Usuario user) {
      
        int i = 1;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
          
            PreparedStatement ps;
            try {
                query.append("update usuario set ");
                query.append("nome = ?, email = ?, senha = ? where login = ?");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, user.getNome());
                ps.setString(i++, user.getEmail());
                ps.setString(i++, user.getSenha());
                ps.setString(i++, user.getLogin());
                ps.executeUpdate();
                
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void insertUsuario(Usuario user) {
      
        int i = 1;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
          
            PreparedStatement ps;
            try {
                query.append("insert into usuario (login, senha, nome, email) ");
                query.append(" values (?, ?, ?, ?)");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, user.getLogin());
                ps.setString(i++, user.getSenha());
                ps.setString(i++, user.getNome());
                ps.setString(i++, user.getEmail());
                
                
                ps.executeUpdate();
                
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void deleteUsuario(Usuario user) {
      
        int i = 1;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
          
            PreparedStatement ps;
            try {
                query.append("delete from usuario where id = ? ");
                ps = conn.prepareStatement(query.toString());
               
                ps.setString(i++, user.getId());
                ps.executeUpdate();
                
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public List<Usuario> getUsuarios(String excetoUserId) {
        int i = 1;
        Usuario u;
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
            ResultSet rs;
            PreparedStatement ps;
            try {
                query.append(" select u.id, u.login, u.nome, u.email from usuario u");
                query.append(" where u.login <> ? ");
                ps = conn.prepareStatement(query.toString());
                ps.setString(1, excetoUserId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getString("u.id"));
                    u.setNome(rs.getString("u.nome"));
                    u.setLogin(rs.getString("u.login"));
                    u.setEmail(rs.getString("u.email"));
                    usuarios.add(u);
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return usuarios;
    }
    
    public List<Usuario> getAmigosUsuario(String idUsuario) {
        int i = 1;
        Usuario u;
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
            ResultSet rs;
            PreparedStatement ps;
            try {
                query.append(" select u.id, u.login, u.nome, u.email from usuario u join usuario_usuario uu ");
                query.append(" on u.id = uu.id_usuario_amigo and uu.id_usuario_logado = ? ");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, idUsuario);
                
                rs = ps.executeQuery();
                while (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getString("u.id"));
                    u.setNome(rs.getString("u.nome"));
                    u.setLogin(rs.getString("u.login"));
                    u.setEmail(rs.getString("u.email"));
                    usuarios.add(u);
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return usuarios;
    }
}
