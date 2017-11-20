/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import exception.EmailJaCadastradoException;
import exception.LoginJaCadastradoException;

import facade.FacadeBankREST;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import model.ContaModel;

import model.TransacaoModel;
import model.UsuarioModel;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;


/**
 *
 * @author roseanealves
 */
@ManagedBean
@SessionScoped
public class RoseBankBean implements Serializable {
    private List<ContaModel> contas = new ArrayList<>();
    private List<UsuarioModel> usuarios = new ArrayList<>();
    private List<TransacaoModel> transacoes = new ArrayList<>();
    private UsuarioModel usuario = new UsuarioModel();
    private ContaModel conta = new ContaModel();
    private TransacaoModel transacao = new TransacaoModel();

    private FacadeBankREST facadeRest = new FacadeBankREST();
    private String mensagem;
    private String acao;
    private Locale localizacao;
    private static final Locale[] COUNTRIES = {Locale.ENGLISH, Locale.forLanguageTag("pt-br")};
    private boolean autorizado;
    private String contaOrigem;
    private String contaDestino;
    private String valor;
    private String texto;
    private String descricaoConta;
    public RoseBankBean() {
        
    }

    public void mudouIdioma(ValueChangeEvent event) {
        FacesContext.getCurrentInstance().getViewRoot().
                setLocale((Locale) event.getNewValue());
    }

    public List<SelectItem> getIdiomasSuportados() {
        List<SelectItem> idiomas = new ArrayList<>();
        for (Locale loc : COUNTRIES) {
            idiomas.add(new SelectItem(loc, loc.getDisplayLanguage(loc)));
        }
        return idiomas;
    }

    public String creditar() {
        conta = facadeRest.findContaById(conta);
        double novoValor = Double.parseDouble(conta.getValorAtual()) + Double.parseDouble(transacao.getValor());
        addMessage(getResourceMessage("transacao_salva"));
        
        conta.setValorAtual(String.valueOf(novoValor));
        facadeRest.editarConta(conta);
        transacao.setContaOrigem(conta.getId());
        transacao.setUsuarioId(getSessionUser().getId());
        transacao.setTipoTransacao("C");
        facadeRest.adicionarTransacao(transacao);
        transacao = new TransacaoModel();
        addMessage(getResourceMessage("transacao_salva"));
        
        return null;
    }
    
    public String debitar() {
        conta = facadeRest.findContaById(conta);
        double valorAtual = Double.parseDouble(conta.getValorAtual());
        double debito = Double.parseDouble(transacao.getValor());
        if (debito > valorAtual) {
            addMessage(getResourceMessage("saldo_insuficiente"));
        } else {
            double novoValor = valorAtual - debito;
            conta.setValorAtual(String.valueOf(novoValor));
            facadeRest.editarConta(conta);
            transacao.setContaOrigem(conta.getId());
            transacao.setUsuarioId(getSessionUser().getId());
            transacao.setTipoTransacao("D");
            facadeRest.adicionarTransacao(transacao);
            transacao = new TransacaoModel();
            addMessage(getResourceMessage("transacao_salva"));
        }
        
        return null;
    }
    
    public void adicionarConta() {
        conta = new ContaModel();
        conta.setIdUsuario(getSessionUser().getId());
        conta.setDsConta(descricaoConta);
        conta.setValorAtual("0");
        facadeRest.adicionarConta(conta);
        conta = new ContaModel();
        descricaoConta = "";
        addMessage(getResourceMessage("conta_adicionada"));
    }
    
    public String salvarCadastro() {
        try {
           
            facadeRest.adicionarUsuario(usuario);
            
            usuario = new UsuarioModel();
            addMessage(getResourceMessage("usuario_cadastrado"));
            return "index";
        } catch (EmailJaCadastradoException | LoginJaCadastradoException e) {
            addMessage(getResourceMessage("usuario_salvo"));
        }

        return null;
    }

    private String getResourceMessage(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resource = context.getApplication().getResourceBundle(context, "msgs");
        return resource.getString(msg);
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String cadastrar() {
        return "cadastro";
    }

    public String cancelar() {
        usuario = new UsuarioModel();
        return "index";
    }

    private UsuarioModel getSessionUser() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        return (UsuarioModel) session.getAttribute("usuario");
    }

    private void setSessionUser() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        if (session.getAttribute("usuario") == null) {
            session.setAttribute("usuario", getUsuario());
        }

    }

    public String loadContas() {
        getContasUsuario();
        return null;
    }

    public String loadTransacoes() {
        transacoes = facadeRest.getTransacoesPorUsuario(getSessionUser());
        return null;
    }

    public String getContasUsuario() {
        setContas(facadeRest.getContasPorUsuario(usuario));
        return null;
    }

    public String deleteConta(ContaModel conta) {
        facadeRest.deletarConta(conta);
        getContas().remove(conta);
        return null;
    }

    public String deleteUsuario(UsuarioModel user) {
        facadeRest.deletarUsuario(user);
        usuarios.remove(user);
        return null;
    }

    public void changeSessionAcao(String acao) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        session.removeAttribute("acao");
        session.setAttribute("acao", acao);
    }

    public String setAcaoAlterar(ContaModel c) {
        changeSessionAcao("conta_alt");
        setSessionConta(c);
        return null;
    }

    public String setAcaoAlterarUsuario(UsuarioModel u) {
        changeSessionAcao("user_alt");
        setSessionUserSelecionado(u);
        return null;
    }

    public void setSessionUserSelecionado(UsuarioModel userSelecionado) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        if (session.getAttribute("usuarioSelecionado") != null) {
            session.removeAttribute("usuarioSelecionado");
        }
        session.setAttribute("usuarioSelecionado", userSelecionado);
    }

    private ContaModel getSessionConta() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        return (ContaModel) session.getAttribute("conta");
    }

    private void setSessionConta(ContaModel c) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        if (session.getAttribute("conta") == null) {
            session.setAttribute("conta", c);
        }
    }

    public String updateConta(ContaModel c) {
        facadeRest.editarConta(c);
        return null;
    }

    public String updateUsuario(UsuarioModel u) {
        facadeRest.editarUsuario(u);
        
        return null;
    }
    
    public void onEdit(RowEditEvent event) {        
        ContaModel c = (ContaModel) event.getObject();
        facadeRest.editarConta(c);
        addMessage(getResourceMessage("conta_editada"));
    }
    
    public void onRowEdit(RowEditEvent event) {
        ContaModel c = (ContaModel) event.getObject();
        facadeRest.editarConta(c);
        addMessage(getResourceMessage("conta_editada"));
    }
    
    public String autenticar() {
        this.usuario = facadeRest.getUsuarioPorLoginSenha(usuario);
        if (this.usuario != null) {
            setSessionUser();
            setAutorizado(true);
            return "/protected/principal";
        }
        setAutorizado(false);
        addMessage(getResourceMessage("login_senha_invalid"));

        return "/index";
    }

    public String sair() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index";
    }

    public void onDelete(ContaModel c) {
        facadeRest.apagarTransacoesPorContaId(c);
        facadeRest.deletarConta(c);
        contas.remove(c);
        addMessage(getResourceMessage("conta_excluida"));
    }
    
    public void onTabChange(TabChangeEvent event) {
        if (event != null) {
            switch (event.getTab().getId()) {
            case "manageContaTab":
                loadContas();
                break;
            case "contaTab":
                loadContas();
                break;
            case "transacaoTab":
                loadTransacoes();
                break;
            default:
                break;
            }
        }
        
    }

    public Locale getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Locale localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * @return the usuario
     */
    public UsuarioModel getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the conta
     */
    public ContaModel getConta() {
        return conta;
    }

    /**
     * @param conta the conta to set
     */
    public void setConta(ContaModel conta) {
        this.conta = conta;
    }

    /**
     * @return the acao
     */
    public String getAcao() {
        return acao;
    }

    /**
     * @param acao the acao to set
     */
    public void setAcao(String acao) {
        this.acao = acao;
    }

    /**
     * @return the contas
     */
    public List<ContaModel> getContas() {
        return contas;
    }

    /**
     * @param contas the contas to set
     */
    public void setContas(List<ContaModel> contas) {
        this.contas = contas;
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioModel> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioModel> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the transacoes
     */
    public List<TransacaoModel> getTransacoes() {
        return transacoes;
    }

    /**
     * @param transacoes the transacoes to set
     */
    public void setTransacoes(List<TransacaoModel> transacoes) {
        this.transacoes = transacoes;
    }

    /**
     * @return the autorizado
     */
    public boolean isAutorizado() {
        return autorizado;
    }

    /**
     * @param autorizado the autorizado to set
     */
    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
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
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the transacao
     */
    public TransacaoModel getTransacao() {
        return transacao;
    }

    /**
     * @param transacao the transacao to set
     */
    public void setTransacao(TransacaoModel transacao) {
        this.transacao = transacao;
    }

    /**
     * @return the descricaoConta
     */
    public String getDescricaoConta() {
        return descricaoConta;
    }

    /**
     * @param descricaoConta the descricaoConta to set
     */
    public void setDescricaoConta(String descricaoConta) {
        this.descricaoConta = descricaoConta;
    }

    /**
     * @return the mensagem
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * @param mensagem the mensagem to set
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
