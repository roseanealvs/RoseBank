/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Exceptions.EmailJaExisteException;
import Exceptions.UsuarioJaExisteException;
import facade.FacadeBank;
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
import model.Conta;
import model.Transacao;
import model.Usuario;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;
import rest.ContaRESTClient;
import rest.UsuarioRESTClient;

/**
 *
 * @author roseanealves
 */
@ManagedBean
@SessionScoped
public class RoseBankBean implements Serializable {
    private List<Conta> contas = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Transacao> transacoes = new ArrayList<>();
    private Usuario usuario = new Usuario();
    private Conta conta = new Conta();
    private FacadeBank facade = new FacadeBank();
    private FacadeBankREST facadeRest = new FacadeBankREST();
    private String message;
    private String acao;
    private Locale localizacao;
    private static final Locale[] COUNTRIES = {Locale.ENGLISH, Locale.forLanguageTag("pt-br")};
    private boolean autorizado;

    public RoseBankBean() {
        message = null;
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

    public void adicionarAmigo() {
        facade.adicionarAmigo();
        addMessage(getResourceMessage("usuario_salvo"));
    }
    
    public String salvarCadastro() {
        try {
            message = null;
            facade.inserirUsuario(usuario);
            usuario = new Usuario();
            return "/index";
        } catch (UsuarioJaExisteException | EmailJaExisteException e) {
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
        usuario = new Usuario();
        return "index";
    }

    private Usuario getSessionUser() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        return (Usuario) session.getAttribute("usuario");
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

    public String loadUsuarios() {
        getUsuariosList();
        return null;
    }

    public String loadTransacoes() {
        changeSessionAcao("tran_con");
        transacoes = facade.getTransacoes(getSessionUser().getLogin());
        return null;
    }

    public String getUsuariosList() {
        usuarios = facade.getUsuarios(getSessionUser().getLogin());
        return null;
    }

    public String getContasUsuario() {
        setContas(facade.getContasPorUsuario(getSessionUser().getLogin()));
        return null;
    }

    public String deleteConta(Conta conta) {
        facade.deleteConta(conta.getId());
        getContas().remove(conta);
        changeSessionAcao("conta_con");
        return null;
    }

    public String deleteUsuario(Usuario user) {
        facade.deleteUsuario(user);
        usuarios.remove(user);
        return null;
    }

    public void changeSessionAcao(String acao) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        session.removeAttribute("acao");
        session.setAttribute("acao", acao);
    }

    public String setAcaoAlterar(Conta c) {
        changeSessionAcao("conta_alt");
        setSessionConta(c);
        return null;
    }

    public String setAcaoAlterarUsuario(Usuario u) {
        changeSessionAcao("user_alt");
        setSessionUserSelecionado(u);
        return null;
    }

    public void setSessionUserSelecionado(Usuario userSelecionado) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        if (session.getAttribute("usuarioSelecionado") != null) {
            session.removeAttribute("usuarioSelecionado");
        }
        session.setAttribute("usuarioSelecionado", userSelecionado);
    }

    private Conta getSessionConta() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        return (Conta) session.getAttribute("conta");
    }

    private void setSessionConta(Conta c) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        if (session.getAttribute("conta") == null) {
            session.setAttribute("conta", c);
        }
    }

    public String updateConta(Conta c) {
        facadeRest.editarConta(c);
        return null;
    }

    public String updateUsuario(Usuario u) {
        facadeRest.editarUsuario(u);
        
        return null;
    }
    
    public void onEdit(RowEditEvent event) {        
        Conta c = (Conta) event.getObject();
        ContaRESTClient rest = new ContaRESTClient();
        rest.edit(conta);
        FacesMessage msg = new FacesMessage("Conta atualizada", c.getDescricao());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public String autenticar() {
        if (getFacade().logar(getUsuario())) {
            setSessionUser();
            setAutorizado(true);
            return "/protected/principal";
        }
        setAutorizado(false);
        addMessage("login/senha inválidos");

        return "/index";
    }

    public String sair() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index";
    }

    public void onTabChange(TabChangeEvent event) {
        if (event != null) {
            switch (event.getTab().getId()) {
            case "userTab":
                loadUsuarios();
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
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the conta
     */
    public Conta getConta() {
        return conta;
    }

    /**
     * @param conta the conta to set
     */
    public void setConta(Conta conta) {
        this.conta = conta;
    }

    /**
     * @return the facade
     */
    public FacadeBank getFacade() {
        return facade;
    }

    /**
     * @param facade the facade to set
     */
    public void setFacade(FacadeBank facade) {
        this.facade = facade;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
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
    public List<Conta> getContas() {
        return contas;
    }

    /**
     * @param contas the contas to set
     */
    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the transacoes
     */
    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    /**
     * @param transacoes the transacoes to set
     */
    public void setTransacoes(List<Transacao> transacoes) {
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

}
