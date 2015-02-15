package example.com.rocketinternettest.database;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt");
    }
}
