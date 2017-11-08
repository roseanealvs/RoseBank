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
import model.Transacao;
import model.Usuario;

/**
 *
 * @author roseanealves
 */
public class UsuarioJSON {
    public static String toJSON(Usuario usuario) {
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

    public static Usuario fromJSONObject(String json) {
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonObject obj = reader.readObject();
        reader.close();
        Usuario u = new Usuario();
        u.setLogin(obj.getString("login"));
        u.setSenha(obj.getString("senha"));
        u.setNome(obj.getString("nome"));
        u.setId(obj.getString("id"));
        u.setEmail(obj.getString("email"));
        return u;
    }

    public static List<Usuario> fromJSONArray(
            String json) {
        List<Usuario> usuarios = new ArrayList<>();
        JsonReader reader = Json.createReader(
                new StringReader(json));
        JsonArray usuarioArray = reader.readArray();
        reader.close();
        for (JsonValue value : usuarioArray) {
            JsonObject obj = (JsonObject) value;
            Usuario u = new Usuario();
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
