package example.com.rocketinternettest.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import example.com.rocketinternettest.Stream;
import example.com.rocketinternettest.database.StreamListDao;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class StreamRetriever {
    private final static String TAG = "StreamRetriever";
    private ConnectionFactory connectionFactory;
    private JsonParser jsonParser;
    private StreamListDao streamListDao;

    public StreamRetriever(Context context) {
        connectionFactory = new ConnectionFactory();
        jsonParser = new JsonParser();
        streamListDao = new StreamListDao(context);
    }

    public List<Stream> fetchStreamList() throws IOException {
        HttpsURLConnection connection = connectionFactory.getConnectionForBaseUrl();
        List<Stream> stream;
        try {
            stream = jsonParser.parseStreamList(connection.getInputStream());
            streamListDao.clearStream();
            streamListDao.storeStream(stream);
            return stream;
        } catch (JSONException e) {
            Log.e(TAG, "JsonException!", e);
            throw new IOException(e);
        } catch (SQLException e) {
            Log.e(TAG, "SQLException!", e);
            return new ArrayList<>();
        }
    }

    public List<Stream> getLocallyStored() throws SQLException {
        return streamListDao.getStream();
    }
}