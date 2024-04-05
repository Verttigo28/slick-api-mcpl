package ch.verttigo.sapi.cache;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.rest.ReqUser;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.json.JSONObject;

import java.time.Duration;
import java.util.UUID;

public class lCache {
    public static LoadingCache<UUID, JSONObject> localCache = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(60)).build(uuid -> ReqUser.getUser(uuid, true));

    public static JSONObject getUser(UUID uuid) {
        return localCache.get(uuid);
    }

    public static void setUser(UUID uuid, JSONObject obj) {
        if (hasUC(uuid) && !obj.getString("serverUUID").equalsIgnoreCase(SAPI.getUUID().toString())) {
            localCache.invalidate(uuid);
            localCache.put(uuid, obj);
        }
    }
    private static Boolean hasUC(UUID uuid) {
        return localCache.getIfPresent(uuid) != null;
    }
}
