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
import model.UsuarioModel;

/**
 *
 * @author roseanealves
 */
public class UsuarioJSON {
    public static String toJSON(UsuarioModel usuario) {
        JsonObject usuarioJSON
                = Json.createObjectBuilder().
                add("login", usuario.getLogin()).
                add("senha", usuario.getSenha()).
                add("nome", usuario.getNome()).
                add("id", usuario. getId()).
                add("email", usuario.getEmail()).
               
                build();
        return usuarioJSON.toString();
    }

    public static UsuarioModel fromJSONObject(String json) {
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonObject obj = reader.readObject();
        reader.close();
        if (!obj.isEmpty()) {
            UsuarioModel u = new UsuarioModel();
            u.setLogin(obj.getString("login"));
            u.setSenha(obj.getString("senha"));
            u.setNome(obj.getString("nome"));
            u.setId(String.valueOf(obj.getInt("id")));
            u.setEmail(obj.getString("email"));
            return u;
        }
        
        return null;
    }

    public static List<UsuarioModel> fromJSONArray(
            String json) {
        List<UsuarioModel> usuarios = new ArrayList<>();
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonArray usuarioArray = reader.readArray();
        reader.close();
        for (JsonValue value : usuarioArray) {
            JsonObject obj = (JsonObject) value;
            UsuarioModel u = new UsuarioModel();
            u.setLogin(obj.getString("login"));
            u.setSenha(obj.getString("senha"));
            u.setNome(obj.getString("nome"));
            u.setId(obj.getString("id"));
            u.setEmail(obj.getString("email"));
            usuarios.add(u);
        }
        return usuarios;
    }
}
