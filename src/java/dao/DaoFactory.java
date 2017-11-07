/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import java.sql.Connection;
import java.sql.DriverManager;



/**
 *
 * @author roseanealves
 */
public class DaoFactory {
    private static final String USUARIO = "root";
    private static final String SENHA = "1234";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/roseBank";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static UsuarioDao usuarioDaoInstance;
    private static ContaDao contaDaoInstance;
    private static TransacaoDao transacaoDaoInstance;
    // Conectar ao banco
    public static Connection getConnection() throws Exception {
        // Registrar o driver
        Class.forName(DRIVER);
        // Capturar a conexão
        Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        // Retorna a conexao aberta
        return conn;
    }

    /**
     * @return the usuarioDaoInstance
     */
    public static UsuarioDao getUsuarioDaoInstance() {
        if (usuarioDaoInstance == null) {
            usuarioDaoInstance = new UsuarioDao();
        }
        return usuarioDaoInstance;
    }

    /**
     * @return the contaDaoInstance
     */
    public static ContaDao getContaDaoInstance() {
        if (contaDaoInstance == null) {
            contaDaoInstance = new ContaDao();
        }
        return contaDaoInstance;
    }

    /**
     * @return the transacaoDaoInstance
     */
    public static TransacaoDao getTransacaoDaoInstance() {
        if (transacaoDaoInstance == null) {
            transacaoDaoInstance = new TransacaoDao();
        }
        return transacaoDaoInstance;
    }

}
