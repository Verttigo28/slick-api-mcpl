package ch.verttigo.sapi.nats;

import ch.verttigo.sapi.SAPI;
import io.nats.client.Connection;
import io.nats.client.Nats;

import java.io.IOException;

import static ch.verttigo.sapi.nats.sub.ReplaceUC.subRUC;

public class natsClient {

    static Connection nc = null;

    public static void connectNats() {
        try {
            nc = Nats.connect(SAPI.getInstance().getConfig().getString("nats.url"));
            System.out.println("Nats launched");
            subRUC();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getNats() {
        return nc;
    }
}
