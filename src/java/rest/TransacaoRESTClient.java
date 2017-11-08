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
import model.Conta;
import model.Transacao;

/**
 *
 * @author roseanealves
 */
public class TransacaoRESTClient {
    public static final String WEBSERVICE_URL
            = "http://localhost:8080/RESTRoseBank/webresources/";
    private String response;
    
    public List<Conta> findAll() {
        Client client = ClientBuilder.newClient();
        response = client.target(WEBSERVICE_URL
                + "service.transacao").
                request(MediaType.APPLICATION_JSON).
                get(String.class);
        client.close();
        return ContaJSON.fromJSONArray(response);
    }

    public Conta find(Long id) {
        Client client = ClientBuilder.newClient();
        response = client.target(WEBSERVICE_URL
                + "service.transacao/" + id).
                request(MediaType.APPLICATION_JSON)
                .get(String.class);
        client.close();
        return ContaJSON.fromJSONObject(response);
    }

    public void create(Transacao transacao) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL + "service.transacao").
                request(MediaType.APPLICATION_JSON).
                post(Entity.entity(transacao,
                        MediaType.APPLICATION_JSON));
        client.close();
    }

    public void edit(Transacao transacao) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL + "service.transacao/"
                + transacao.getId()).
                request(MediaType.APPLICATION_JSON).
                put(Entity.entity(transacao,
                        MediaType.APPLICATION_JSON));
        client.close();
    }

    public void delete(Long id) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL
                + "service.transacao/" + id).
                request(MediaType.APPLICATION_JSON).
                delete();
        client.close();
    }
}
