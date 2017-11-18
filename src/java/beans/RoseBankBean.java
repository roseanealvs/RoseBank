/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


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
import model.ContaModel;
import model.DAO;
import model.TransacaoModel;
import model.UsuarioModel;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;
import rest.ContaRESTClient;

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
//        try {
            message = null;
            DAO dao = new DAO(UsuarioModel.class);
            dao.adicionar(usuario);
            
            usuario = new UsuarioModel();
            return "/index";
//        } catch (UsuarioJaExisteException | EmailJaExisteException e) {
//            addMessage(getResourceMessage("usuario_salvo"));
//        }

//        return null;
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
        DAO dao = new DAO(ContaModel.class);
        setContas(dao.listarGenerico("Conta.findByIdUsuario"));
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
        ContaRESTClient rest = new ContaRESTClient();
        rest.edit(conta);
        FacesMessage msg = new FacesMessage("Conta atualizada", c.getDescricao());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public String autenticar() {
        DAO dao = new DAO(UsuarioModel.class);
        if (dao.listarGenerico("Usuario.findByLoginAndSenha", usuario.getLogin(), usuario.getSenha()) == null) {
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

}
