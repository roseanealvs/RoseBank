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
public class TransacaoModel {
    private String dsTransacao;
    private String dataTransacao;
    private String valor;
    private String usuario;
    private String contaOrigem;
    private String contaDestino;
    private String usuarioId;
    private String id;
    private String tipoTransacao;
    /**
     * @return the descricao
     */
    public String getDsTransacao() {
        return dsTransacao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDsTransacao(String descricao) {
        this.dsTransacao = descricao;
    }

    /**
     * @return the data
     */
    public String getDataTransacao() {
        return dataTransacao;
    }

    /**
     * @param data the data to set
     */
    public void setDataTransacao(String data) {
        this.dataTransacao = data;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the contaOrigem
     */
    public String getContaOrigem() {
        return contaOrigem;
    }

    /**
     * @param contaOrigem the contaOrigem to set
     */
    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
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
     * @return the contaDestino
     */
    public String getContaDestino() {
        return contaDestino;
    }

    /**
     * @param contaDestino the contaDestino to set
     */
    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    /**
     * @return the idUsuario
     */
    public String getUsuarioId() {
        return usuarioId;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setUsuarioId(String idUsuario) {
        this.usuarioId = idUsuario;
    }

    /**
     * @return the tipoTransacao
     */
    public String getTipoTransacao() {
        return tipoTransacao;
    }

    /**
     * @param tipoTransacao the tipoTransacao to set
     */
    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

}
