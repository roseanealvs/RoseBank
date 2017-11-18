/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author roseanealves
 */
@Entity
@Table(name = "transacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transacao.findAll", query = "SELECT t FROM Transacao t"),
    @NamedQuery(name = "Transacao.findById", query = "SELECT t FROM Transacao t WHERE t.id = :id"),
    @NamedQuery(name = "Transacao.findByDsTransacao", query = "SELECT t FROM Transacao t WHERE t.dsTransacao = :dsTransacao"),
    @NamedQuery(name = "Transacao.findByDataTransacao", query = "SELECT t FROM Transacao t WHERE t.dataTransacao = :dataTransacao"),
    @NamedQuery(name = "Transacao.findByValor", query = "SELECT t FROM Transacao t WHERE t.valor = :valor"),
    @NamedQuery(name = "Transacao.findByContaOrigem", query = "SELECT t FROM Transacao t WHERE t.contaOrigem = :contaOrigem"),
    @NamedQuery(name = "Transacao.findByContaDestino", query = "SELECT t FROM Transacao t WHERE t.contaDestino = :contaDestino"),
    @NamedQuery(name = "Transacao.findByLoginUsuario", query = "SELECT t FROM Transacao t WHERE t.loginUsuario = :loginUsuario")})
public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "ds_transacao")
    private String dsTransacao;
    @Column(name = "data_transacao")
    @Temporal(TemporalType.DATE)
    private Date dataTransacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "conta_origem")
    private Integer contaOrigem;
    @Column(name = "conta_destino")
    private Integer contaDestino;
    @Size(max = 100)
    @Column(name = "login_usuario")
    private String loginUsuario;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idUsuario;

    public Transacao() {
    }

    public Transacao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDsTransacao() {
        return dsTransacao;
    }

    public void setDsTransacao(String dsTransacao) {
        this.dsTransacao = dsTransacao;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Integer contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Integer getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Integer contaDestino) {
        this.contaDestino = contaDestino;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transacao)) {
            return false;
        }
        Transacao other = (Transacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Transacao[ id=" + id + " ]";
    }
    
}
