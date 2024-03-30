package ch.verttigo.sapi.utils;

import ch.verttigo.sapi.SAPI;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HTTPUtils {

    public static Pair<Integer, JSONObject> postRequest(String path, JSONObject jsonObject) {
        String key = SAPI.getInstance().getConfig().getString("API.auth");
        path += "&serverUUID=" + SAPI.getUUID();

        int statusCode;
        JSONObject result;

        //HTTP PART
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();
        SimpleHttpRequest request = SimpleRequestBuilder.post(path)
                .setBody(String.valueOf(jsonObject), ContentType.APPLICATION_JSON)
                .addHeader("Accept", "application/json")
                .addHeader("Content-type", "application/json")
                .addHeader("Authorization", key)
                .build();
        try {

            Future<SimpleHttpResponse> future = client.execute(request, null);
            SimpleHttpResponse response = future.get();
            statusCode = response.getCode();

            if (statusCode != 200 && statusCode != 201) return new Pair<>(statusCode, null);

            result = new JSONObject(response.getBodyText());
            client.close();
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(statusCode, result);
    }

    public static Pair<Integer, JSONObject> getRequest(String path) {
        String key = SAPI.getInstance().getConfig().getString("API.auth");
        path += "&serverUUID=" + SAPI.getUUID();

        int statusCode;
        JSONObject result;

        //HTTP Part
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();
        SimpleHttpRequest request = SimpleRequestBuilder.get(path)
                .addHeader("Authorization", key)
                .build();

        try {
            final Future<SimpleHttpResponse> future = client.execute(request, null);
            SimpleHttpResponse response = future.get();

            statusCode = response.getCode();

            if (statusCode != 200) return new Pair<>(statusCode, null);

            result = new JSONObject(response.getBodyText());

            client.close();
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(statusCode, result);
    }
}
