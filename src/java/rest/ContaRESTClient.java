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
import model.ContaModel;

/**
 *
 * @author roseanealves
 */
public class ContaRESTClient {
    public static final String WEBSERVICE_URL
            = "http://localhost:8080/RESTRoseBank/webresources/";
    private String response;
    
    public List<ContaModel> findAll() {
        Client client = ClientBuilder.newClient();
        response = client.target(WEBSERVICE_URL
                + "service.conta").
                request(MediaType.APPLICATION_JSON).
                get(String.class);
        client.close();
        return ContaJSON.fromJSONArray(response);
    }

    public ContaModel find(Long id) {
        Client client = ClientBuilder.newClient();
        response = client.target(WEBSERVICE_URL
                + "service.conta/" + id).
                request(MediaType.APPLICATION_JSON)
                .get(String.class);
        client.close();
        return ContaJSON.fromJSONObject(response);
    }

    public void create(ContaModel conta) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL + "service.conta").
                request(MediaType.APPLICATION_JSON).
                post(Entity.entity(conta,
                        MediaType.APPLICATION_JSON));
        client.close();
    }

    public void edit(ContaModel conta) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL + "service.conta/"
                + conta.getId()).
                request(MediaType.APPLICATION_JSON).
                put(Entity.entity(conta,
                        MediaType.APPLICATION_JSON));
        client.close();
    }

    public void delete(String id) {
        Client client = ClientBuilder.newClient();
        client.target(WEBSERVICE_URL
                + "service.conta/" + id).
                request(MediaType.APPLICATION_JSON).
                delete();
        client.close();
    }
}
