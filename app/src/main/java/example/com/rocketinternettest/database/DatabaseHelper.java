package example.com.rocketinternettest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import example.com.rocketinternettest.R;
import example.com.rocketinternettest.Stream;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Stream.class);
        } catch (SQLException e) {
            Log.e(TAG, "SQLException!", e);
        }
    }

    private void dropAllTables() {
        try {
            TableUtils.dropTable(connectionSource, Stream.class, true);
        } catch (SQLException e) {
            Log.e(TAG, "SQLException!", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        dropAllTables();
        onCreate(sqLiteDatabase, connectionSource);
    }
}
