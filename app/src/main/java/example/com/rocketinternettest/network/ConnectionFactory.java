package example.com.rocketinternettest.network;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class ConnectionFactory {
    private static final String BASE_URL = "https://www.zalora.com.my/mobile-api/women/clothing/";

    private void addCookieToConnection(HttpsURLConnection connection) {
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
    }

    public HttpsURLConnection getConnectionForBaseUrl() throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(BASE_URL).openConnection();
        addCookieToConnection(connection);
        return connection;
    }
}