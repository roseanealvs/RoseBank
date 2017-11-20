/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import model.TransacaoModel;

/**
 *
 * @author roseanealves
 */
public class TransacaoJSON {
    public static String toJSON(TransacaoModel transacao) {
        JsonObject transacaoJSON
                = Json.createObjectBuilder().
                add("dataTransacao", transacao.getDataTransacao()).
                add("dsTransacao", transacao.getDsTransacao()).
                add("valor", transacao.getValor()).
                add("usuarioId", transacao.getUsuarioId()).
                add("tipoTransacao", transacao.getTipoTransacao()).        
                build();
        return transacaoJSON.toString();
    }

    public static TransacaoModel fromJSONObject(String json) {
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonObject obj = reader.readObject();
        reader.close();
        TransacaoModel t = new TransacaoModel();
            t.setId(String.valueOf(obj.getInt("id")));
            t.setDataTransacao(obj.getString("dataTransacao"));
            t.setDsTransacao(obj.getString("dsTransacao"));
            t.setValor(String.valueOf(obj.get("valor")));
            t.setContaOrigem(String.valueOf(obj.getInt("contaOrigem")));
            t.setUsuarioId(String.valueOf(obj.getInt("usuarioId")));
            t.setTipoTransacao(obj.getString("tipoTransacao"));
        return t;
    }

    public static List<TransacaoModel> fromJSONArray(
            String json) {
        List<TransacaoModel> transacoes = new ArrayList<>();
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonArray contaArray = reader.readArray();
        reader.close();
        for (JsonValue value : contaArray) {
            JsonObject obj = (JsonObject) value;
            TransacaoModel t = new TransacaoModel();
            t.setId(String.valueOf(obj.getInt("id")));
            t.setDataTransacao(obj.getString("dataTransacao"));
            t.setDsTransacao(obj.getString("dsTransacao"));
            t.setValor(String.valueOf(obj.get("valor")));
            t.setContaOrigem(String.valueOf(obj.getInt("contaOrigem")));
            t.setUsuarioId(String.valueOf(obj.getInt("usuarioId")));
            t.setTipoTransacao(obj.getString("tipoTransacao"));
            transacoes.add(t);
        }
        return transacoes;
    }
}
