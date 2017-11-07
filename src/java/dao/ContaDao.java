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
import model.Conta;


/**
 *
 * @author roseanealves
 */
public class ContaDao {

    public List<Conta> getContas(String idUsuario) {
        int i = 1;
        Conta c;
        List<Conta> contas = new ArrayList<>();
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
            ResultSet rs;
            PreparedStatement ps;
            try {
                query.append(" select c.ds_conta, c.id, c.valor_atual from conta c join usuario u where ");
                query.append(" u.id = c.id_usuario and u.login = ? ");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, idUsuario);
                
                rs = ps.executeQuery();
                while (rs.next()) {
                    c = new Conta();
                    c.setDescricao(rs.getString("ds_conta"));
                    c.setId(rs.getString("id"));
                    c.setValorAtual(rs.getString("valor_atual"));
                    contas.add(c);
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return contas;
    }
    
    public Conta getContaPorID(String id) {
       
        int i = 1;
        Conta c = null;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
            ResultSet rs;
            PreparedStatement ps;
            try {
                query.append("select c.ds_conta, c.id, c.valor_atual from conta where c.id = ?");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, id);
                
                rs = ps.executeQuery();
                if (rs.next()) {
                    c = new Conta();
                    c.setDescricao(rs.getString("ds_conta"));
                    c.setId(rs.getString("id"));
                    c.setValorAtual(rs.getString("valor_atual"));
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return c;
    }
    
    public void deleteConta(String id) {
      
        int i = 1;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
          
            PreparedStatement ps;
            try {
                query.append("delete from conta where ");
                query.append("id = ?");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, id);
               
                ps.executeUpdate();
                
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void updateConta(String id, String descricao) {
      
        int i = 1;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
          
            PreparedStatement ps;
            try {
                query.append("update conta set ");
                query.append(" ds_conta = ? ");
                query.append("where id = ?");
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, descricao);
                ps.setString(i++, id);
               
                ps.executeUpdate();
                
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void updateValorConta(String id, String valor) {
      
        int i = 1;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
          
            PreparedStatement ps;
            try {
                query.append(" update conta set ");
                query.append(" valor_atual = ? ");
                query.append(" where id = ? ");
                ps = conn.prepareStatement(query.toString());
                ps.setDouble(i++, Double.parseDouble(valor));
                ps.setString(i++, id);
               
                ps.executeUpdate();
                
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void insertConta(String usuario, String descricao) {
      
        int i = 1;
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
          
            PreparedStatement ps;
            try {
                query.append("insert into conta (ds_conta, id_usuario) ");
                query.append(" values (?, ?) ");
               
                ps = conn.prepareStatement(query.toString());
                ps.setString(i++, descricao);
                ps.setString(i++, usuario);
               
                ps.executeUpdate();
                
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
