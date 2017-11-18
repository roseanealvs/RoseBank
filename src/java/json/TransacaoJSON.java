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
import model.ContaModel;
import model.TransacaoModel;

/**
 *
 * @author roseanealves
 */
public class TransacaoJSON {
    public static String toJSON(TransacaoModel transacao) {
        JsonObject transacaoJSON
                = Json.createObjectBuilder().
                add("data", transacao.getData()).
                add("descricao", transacao.getDescricao()).
                add("valor", transacao.getValor()).
                add("usuario", transacao.getUsuario()).
                add("contaOrigem", transacao.getContaOrigem()).
               
                build();
        return transacaoJSON.toString();
    }

    public static TransacaoModel fromJSONObject(String json) {
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonObject transacaoObject = reader.readObject();
        reader.close();
        TransacaoModel t = new TransacaoModel();
        t.setData(transacaoObject.getString("data"));
        t.setDescricao(transacaoObject.getString("descricao"));
        t.setValor(transacaoObject.getString("valor"));
        t.setUsuario(transacaoObject.getString("usuario"));
        t.setValor(transacaoObject.getString("valor"));
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
            t.setData(obj.getString("data"));
            t.setDescricao(obj.getString("descricao"));
            t.setValor(obj.getString("valor"));
            t.setUsuario(obj.getString("usuario"));
            t.setValor(obj.getString("valor"));
            transacoes.add(t);
        }
        return transacoes;
    }
}
