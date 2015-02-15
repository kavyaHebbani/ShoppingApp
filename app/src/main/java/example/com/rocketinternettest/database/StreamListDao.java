package example.com.rocketinternettest.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import example.com.rocketinternettest.Stream;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class StreamListDao {
    private Database database;

    public StreamListDao(Context context) {
        database = new Database();
        database.Initialize(context);
    }

    public void storeStream(List<Stream> stream) throws SQLException {
        Dao<Stream, Integer> dao = database.getStreamDao();
        try {
            database.startTransaction();
            for (Stream element : stream) {
                dao.createOrUpdate(element);
            }
        } finally {
            database.endTransaction();
        }
    }

    public List<Stream> getStream() throws SQLException {
        Dao<Stream, Integer> dao = database.getStreamDao();
        return dao.queryForAll();
    }

    public void clearStream() throws SQLException {
        database.clearTable(Stream.class);
    }
}
