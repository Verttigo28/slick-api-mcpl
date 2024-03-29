package ch.verttigo.sapi.nats.sub;

import ch.verttigo.sapi.cache.lCache;
import ch.verttigo.sapi.nats.natsClient;
import io.nats.client.Dispatcher;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ReplaceUC {

    public static void subRUC() {
        Dispatcher d = natsClient.getNats().createDispatcher((data) -> {
            String str = new String(data.getData(), StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(str);
            updateUC(obj);
        });
        d.subscribe("RUC");
    }

    private static void updateUC(JSONObject obj) {
        lCache.setUser(UUID.fromString(obj.get("uuid").toString()),obj);
    }
}
