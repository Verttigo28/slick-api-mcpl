package ch.verttigo.sapi.rest;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.cache.lCache;
import ch.verttigo.sapi.utils.HTTPUtils;
import ch.verttigo.sapi.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.UUID;

public class ReqUser {

    //TODO Get key from config
    public static JSONObject getUser(UUID uuid, Boolean createIfNull) {
        String key = "token";
        String path = SAPI.getInstance().getConfig().get("API.url") + "/user?uuid=" + uuid;

        if (createIfNull) {
            //TODO rewrite this part
            String nick = (Bukkit.getPlayer(uuid).getDisplayName() != null) ? Bukkit.getPlayer(uuid).getDisplayName() : "Aie";
            path += "&createIfNull=true&Nickname=" + nick + "&paidAccount=true";
        }

        Pair<Integer, JSONObject> response = HTTPUtils.getRequest(key, path);
        if (response.getLeft() != 200) {
            System.out.println("There was an error getting the user..");
            return null;
        } else {
            return response.getRight();
        }
    }

    public static JSONObject createUser(Player user) {
        String key = null;
        String path = "/user/create";
        JSONObject obj = new JSONObject();

        obj.put("UUID", user.getUniqueId());
        obj.put("Nickname", user.getDisplayName());
        //TODO Need a utils to check if the player'account is premium
        obj.put("paidAccount", true);

        Pair<Integer, JSONObject> response = HTTPUtils.postRequest(key, path, obj);
        if (response.getLeft() != 200) {
            System.out.println("There was an error creating the user..");
            return null;
        } else {
            return response.getRight();
        }

    }

    public static JSONObject updateUser(UUID uuid, JSONObject modifiedData) {
        // String key = SAPI.getInstance().getConfig().getString("API.auth");
        String key = "token";
        String path = SAPI.getInstance().getConfig().get("API.url") + "/user/update/?uuid=" + uuid;

        Pair<Integer, JSONObject> response = HTTPUtils.postRequest(key, path, modifiedData);
        if (response.getLeft() != 200) {
            System.out.println("There was an error updating the user..");
            return null;
        } else {
            lCache.setUser(uuid, response.getRight());
            return response.getRight();
        }

    }

}
