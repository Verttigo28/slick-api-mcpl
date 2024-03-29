package ch.verttigo.sapi.nats;

import io.nats.client.Connection;
import io.nats.client.Nats;

import java.io.IOException;

public class natsClient {

    static Connection nc = null;

    public static void connectNats() {
        try {
            Nats.connect("nats://localhost:4222");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getNats() {
        return nc;
    }
}
