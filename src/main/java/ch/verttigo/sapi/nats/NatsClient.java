package ch.verttigo.sapi.nats;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.utils.Logger;
import io.nats.client.Connection;
import io.nats.client.Nats;

import java.io.IOException;

import static ch.verttigo.sapi.nats.sub.ReplaceUC.subRUC;
import static ch.verttigo.sapi.utils.Logger.log;

public class NatsClient {

    static Connection nc = null;

    public static void connectNats() {
        try {
            nc = Nats.connect(SAPI.getInstance().getConfig().getString("nats.url"));
            log(Logger.LogType.severe, "NATS server available, connection is UP");
            subRUC();
        } catch (IOException | InterruptedException e) {
            log(Logger.LogType.severe, "NATS server not available, shutting down plugin");
            SAPI.getInstance().getServer().getPluginManager().disablePlugin(SAPI.getInstance());
        }
    }

    public static Connection getNats() {
        return nc;
    }
}
