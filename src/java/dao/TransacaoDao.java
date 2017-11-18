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
import model.TransacaoModel;


/**
 *
 * @author roseanealves
 */
public class TransacaoDao {
    public List<TransacaoModel> getTransacoes(String usuarioId) {
        int i = 1;
        TransacaoModel t;
        List<TransacaoModel> transacoes = new ArrayList<>();
        try {
            Connection conn = DaoFactory.getConnection();
            StringBuilder query = new StringBuilder();
            ResultSet rs;
            PreparedStatement ps;
            try {
                query.append(" select ds_transacao, data_transacao, valor, conta_origem from transacao");
                query.append(" where login_usuario = ? ");
                ps = conn.prepareStatement(query.toString());
                ps.setString(1, usuarioId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    t = new TransacaoModel();
                    t.setDescricao(rs.getString("ds_transacao"));
                    t.setData(rs.getString("data_transacao"));
                    t.setValor(rs.getString("valor"));
                    t.setContaOrigem(rs.getString("conta_origem"));
                    transacoes.add(t);
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return transacoes;
    }
}
