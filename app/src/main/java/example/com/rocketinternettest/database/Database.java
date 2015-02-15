package example.com.rocketinternettest.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import example.com.rocketinternettest.Stream;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class Database {

    private static DatabaseHelper databaseHelper = null;

    public Database() {
    }

    public void Initialize(Context context) {
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.getWritableDatabase();
    }

    public Dao<Stream, Integer> getStreamDao() throws SQLException {
        return databaseHelper.getDao(Stream.class);
    }

    public void startTransaction() {
        databaseHelper.getWritableDatabase().beginTransaction();
    }

    public void endTransaction() {
        databaseHelper.getWritableDatabase().setTransactionSuccessful();
        databaseHelper.getWritableDatabase().endTransaction();
    }

    public void clearTable(Class<?> cl) throws SQLException {
        TableUtils.clearTable(databaseHelper.getConnectionSource(), cl);
    }
}
