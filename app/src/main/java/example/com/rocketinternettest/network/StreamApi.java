package example.com.rocketinternettest.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import example.com.rocketinternettest.Stream;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class StreamApi {
    private static List<Stream> cachedElements = new ArrayList<>();
    private StreamRetriever streamRetriever;
    private Context context;

    public StreamApi(Context context) {
        this.context = context;
        streamRetriever = new StreamRetriever(context);
    }

    public List<Stream> getStream(boolean isForcedDownload) throws SQLException, IOException {
        if (isForcedDownload) {
            updateStream();
        } else {
            if (cachedElements == null || cachedElements.isEmpty()) {
                getLocalOrRemoteStream();
            }
        }
        return cachedElements;
    }

    private void getLocalOrRemoteStream() throws IOException, SQLException {
        cachedElements = streamRetriever.getLocallyStored();
        if (cachedElements == null || cachedElements.isEmpty()) {
            updateStream();
        }
    }

    public void updateStream() throws IOException {
        if (isInternetOn()) {
            cachedElements = streamRetriever.fetchStreamList();
        }
    }

    private boolean isInternetOn() {
        try {
            ConnectivityManager connection = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connection.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                    || connection.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
                return true;
            } else if (connection.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
                    || connection.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
                return false;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}