/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import json.ContaJSON;
import json.UsuarioJSON;
import model.ContaModel;
import model.UsuarioModel;

/**
 *
 * @author roseanealves
 */
public class UsuarioRESTClient {
    public static final String WEBSERVICE_URL
            = "http://localhost:8080/RESTRoseBank/webresources/";
    private String response;
    
    public List<UsuarioModel> findAll() {
        Client client = ClientBuilder.newClient();
        response = client.target(WEBSERVICE_URL
                + "service.usuario").
                request(MediaType.APPLICATION_JSON).
                get(String.class);
        client.close();
        return UsuarioJSON.fromJSONArray(response);
    }

    public UsuarioModel findUserByLoginAndPassword(String login, String senha) {
        Client client = ClientBuilder.newClient();
        response = client.target(WEBSERVICE_URL
                + "service.usuario/" + login + "/" + senha).
                request(MediaType.APPLICATION_JSON)
                .get(String.class);
        client.close();
        return UsuarioJSON.fromJSONObject(response);
    }
    
    public UsuarioModel find(Long id) {
        Client client = ClientBuilder.newClient();
        response = client.target(WEBSERVICE_URL
                + "service.usuario/" + id).
                request(MediaType.APPLICATION_JSON)
                .get(String.class);
        client.close();
        return UsuarioJSON.fromJSONObject(response);
    }

    public void create(UsuarioModel usuario) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL + "service.usuario").
                request(MediaType.APPLICATION_JSON).
                post(Entity.entity(usuario,
                        MediaType.APPLICATION_JSON));
        client.close();
    }

    public void edit(UsuarioModel usuario) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL + "service.usuario/"
                + usuario.getId()).
                request(MediaType.APPLICATION_JSON).
                put(Entity.entity(usuario,
                        MediaType.APPLICATION_JSON));
        client.close();
    }

    public void delete(String id) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL
                + "service.usuario/" + id).
                request(MediaType.APPLICATION_JSON).
                delete();
        client.close();
    }
}
