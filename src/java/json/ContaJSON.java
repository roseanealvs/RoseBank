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

/**
 *
 * @author roseanealves
 */
public class ContaJSON {
    public static String toJSON(ContaModel conta) {
        JsonObject contaJSON
                = Json.createObjectBuilder().
                add("id", conta.getId()).
                add("descricao", conta.getDsConta()).
                add("valorAtual", conta.getValorAtual()).
                add("id", conta.getId()).
                add("idUsuario", conta.getIdUsuario()).
                add("valorTransferir", conta.getValorTransferir()).
                build();
        return contaJSON.toString();
    }

    public static ContaModel fromJSONObject(String json) {
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonObject contaObject = reader.readObject();
        reader.close();
        ContaModel c = new ContaModel();
        c.setId(String.valueOf(contaObject.getInt("id")));
        c.setDsConta(contaObject.getString("dsConta"));
        c.setValorAtual(String.valueOf(contaObject.get("valorAtual")));
        c.setIdUsuario(String.valueOf(contaObject.getInt("idUsuario")));
      
        return c;
    }

    public static List<ContaModel> fromJSONArray(
            String json) {
        List<ContaModel> contas = new ArrayList<>();
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonArray contaArray = reader.readArray();
        reader.close();
        for (JsonValue value : contaArray) {
            JsonObject obj = (JsonObject) value;
            ContaModel c = new ContaModel();
            c.setId(String.valueOf(obj.getInt("id")));
            c.setDsConta(obj.getString("dsConta"));
            c.setValorAtual(String.valueOf(obj.getInt("valorAtual")));
            c.setIdUsuario(String.valueOf(obj.getInt("idUsuario")));
            contas.add(c);
        }
        return contas;
    }
}
