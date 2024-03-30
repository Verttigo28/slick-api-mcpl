package ch.verttigo.sapi.cache;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.rest.ReqUser;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.time.Duration;
import java.util.UUID;

public class lCache {

    static LoadingCache<UUID, JSONObject> localCache = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(60)).build(uuid -> ReqUser.getUser(uuid, true));

    public static JSONObject getUser(UUID uuid) {
        return localCache.get(uuid);
    }

    public static void setUser(UUID uuid, JSONObject obj) {
        if (hasUC(uuid) && obj.get("serverUUID") != SAPI.getUUID().toString()) {
            //TODO is there a better way ?
            localCache.invalidate(uuid);
            localCache.put(uuid, obj);
        }
    }

    //TODO - Is it better to update only the cache present or take any update from the network ?
    //If I have 10 lobby but a 100 game server, the 100 server does not need the update because they will not live long enough to use that data.
    //So no, Asking 110 server to update a cache for a potential spread of 10, not worth it.
    private static Boolean hasUC(UUID uuid) {
        return localCache.getIfPresent(uuid) != null;
    }
}
