package ch.verttigo.sapi.utils;

import ch.verttigo.sapi.SAPI;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class HTTPUtils {

    public static Pair<Integer, JSONObject> postRequest(String path, JSONObject jsonObject) {
        String key = SAPI.getInstance().getConfig().getString("API.auth");
        CloseableHttpClient client = HttpClients.createDefault();
        path += "&serverUUID=" + SAPI.getUUID();
        HttpPost httpPost = new HttpPost(path);
        int statusCode = 404;
        JSONObject result = null;

        try {
            StringEntity entity = new StringEntity(jsonObject.toString());
            httpPost.setEntity(entity);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json");
            httpPost.addHeader("Authorization", key);

            CloseableHttpResponse response = client.execute(httpPost);
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200 && statusCode != 201) return new Pair<>(statusCode, null);

            result = new JSONObject(EntityUtils.toString(response.getEntity()));
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(statusCode, result);
    }

    public static Pair<Integer, JSONObject> getRequest(String path) {
        String key = SAPI.getInstance().getConfig().getString("API.auth");
        CloseableHttpClient client = HttpClients.createDefault();
        path += "&serverUUID=" + SAPI.getUUID();
        HttpGet httpGet = new HttpGet(path);
        int statusCode = 0;
        JSONObject result = null;
        httpGet.addHeader("Authorization", key);

        try {
            CloseableHttpResponse response = client.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) return new Pair<>(statusCode, null);

            result = new JSONObject(EntityUtils.toString(response.getEntity()));

            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(statusCode, result);
    }

    public static boolean deleteRequest(String path) throws IOException {
        String key = SAPI.getInstance().getConfig().getString("API.auth");
        CloseableHttpClient client = HttpClients.createDefault();
        path += "&serverUUID=" + SAPI.getUUID();
        HttpDelete httpDelete = new HttpDelete(path);

        httpDelete.addHeader("Authorization", key);

        CloseableHttpResponse response = client.execute(httpDelete);
        int statusCode = response.getStatusLine().getStatusCode();
        client.close();

        return statusCode == 200;

    }

    public static boolean putRequest(String path) throws IOException {
        String key = SAPI.getInstance().getConfig().getString("API.auth");
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut HttpPut = new HttpPut(path);

        HttpPut.addHeader("Authorization", key);

        CloseableHttpResponse response = client.execute(HttpPut);
        int statusCode = response.getStatusLine().getStatusCode();
        client.close();

        return statusCode == 200;
    }

}
