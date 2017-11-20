/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author roseanealves
 */
public class ContaModel {
    private String dsConta;
    private String valorAtual;
    private String id;
    private String idUsuario;
    private String valorTransferir;
  
    /**
     * @return the valorAtual
     */
    public String getValorAtual() {
        return valorAtual;
    }

    /**
     * @param valorAtual the valorAtual to set
     */
    public void setValorAtual(String valorAtual) {
        this.valorAtual = valorAtual;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the descricao
     */
    public String getDsConta() {
        return dsConta;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDsConta(String descricao) {
        this.dsConta = descricao;
    }

    /**
     * @return the idUsuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the valorTransferir
     */
    public String getValorTransferir() {
        return valorTransferir;
    }

    /**
     * @param valorTransferir the valorTransferir to set
     */
    public void setValorTransferir(String valorTransferir) {
        this.valorTransferir = valorTransferir;
    }

}
