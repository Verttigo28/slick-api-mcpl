package ch.verttigo.sapi.rest;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.utils.HTTP;
import ch.verttigo.sapi.utils.Logger;
import ch.verttigo.sapi.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.UUID;

import static ch.verttigo.sapi.cache.lCache.localCache;
import static ch.verttigo.sapi.utils.Logger.log;

public class ReqUser {

    public static JSONObject getUser(UUID uuid, Boolean createIfNull) {
        String path = SAPI.getInstance().getConfig().get("API.url") + "/user?uuid=" + uuid;

        if (createIfNull) {
            //TODO rewrite this part
            String nick = (Bukkit.getPlayer(uuid).getDisplayName() != null) ? Bukkit.getPlayer(uuid).getDisplayName() : "Aie";
            path += "&createIfNull=true&Nickname=" + nick + "&paidAccount=true";
        }

        Pair<Integer, JSONObject> response = HTTP.getRequest(path);
        if (response.getLeft() != 200) {
            log(Logger.LogType.severe, "REST server return the error code " + response.getLeft() + ", check REST server log");
            return null;
        } else {
            return response.getRight();
        }
    }

    public static JSONObject createUser(Player user) {
        String path = "/user/create";
        JSONObject obj = new JSONObject();

        obj.put("UUID", user.getUniqueId());
        obj.put("Nickname", user.getDisplayName());
        //TODO Need a utils to check if the player'account is premium
        obj.put("paidAccount", true);

        Pair<Integer, JSONObject> response = HTTP.postRequest(path, obj);
        if (response.getLeft() != 200) {
            log(Logger.LogType.severe, "REST server return the error code " + response.getLeft() + ", check REST server log");
            return null;
        } else {
            return response.getRight();
        }

    }

    public static JSONObject updateUser(UUID uuid, JSONObject modifiedData) {
        String path = SAPI.getInstance().getConfig().get("API.url") + "/user/update/?uuid=" + uuid;

        Pair<Integer, JSONObject> response = HTTP.postRequest(path, modifiedData);
        if (response.getLeft() != 200) {
            log(Logger.LogType.severe, "REST server return the error code " + response.getLeft() + ", check REST server log");
            return null;
        } else {
            localCache.invalidate(uuid);
            localCache.put(uuid, response.getRight());
            return response.getRight();
        }

    }

}
