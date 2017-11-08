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
import model.Conta;

/**
 *
 * @author roseanealves
 */
public class ContaJSON {
    public static String toJSON(Conta conta) {
        JsonObject contaJSON
                = Json.createObjectBuilder().
                add("id", conta.getId()).
                add("descricao", conta.getDescricao()).
                add("valorAtual", conta.getValorAtual()).
                add("id", conta.getId()).
                add("idUsuario", conta.getIdUsuario()).
                add("valorTransferir", conta.getValorTransferir()).
                build();
        return contaJSON.toString();
    }

    public static Conta fromJSONObject(String json) {
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonObject contaObject = reader.readObject();
        reader.close();
        Conta c = new Conta();
        c.setId(contaObject.getString("id"));
        c.setDescricao(contaObject.getString("descricao"));
        c.setValorAtual(contaObject.getString("valorAtual"));
        c.setIdUsuario(contaObject.getString("idUsuario"));
        c.setValorTransferir(contaObject.getString("valorTransferir"));
        return c;
    }

    public static List<Conta> fromJSONArray(
            String json) {
        List<Conta> contas = new ArrayList<>();
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonArray contaArray = reader.readArray();
        reader.close();
        for (JsonValue value : contaArray) {
            JsonObject obj = (JsonObject) value;
            Conta c = new Conta();
            c.setId(obj.getString("id"));
            c.setDescricao(obj.getString("descricao"));
            c.setValorAtual(obj.getString("valorAtual"));
            c.setIdUsuario(obj.getString("idUsuario"));
            c.setValorTransferir(obj.getString("valorTransferir"));
            contas.add(c);
        }
        return contas;
    }
}
