package cmu.sv.flubber.ihere.dbLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class DBLocalConnector implements DBLocalConnectorInterface {
        // tag
        private static final String TAG = "DatabaseConnector";

        // database info
        public static final String DATABASE_NAME = "USERINFO";
        private SQLiteDatabase database; // database object
        private DatabaseOpenHelper databaseOpenHelper; // database helper

        // table names
        public static final String TABLE_NAME = "user";

        // studentQuizes columns
        public static final String KEY_USER_ID = "id";
        public static final String KEY_USER_NAME = "name";
        public static final String KEY_USER_EMAIL = "email";
        public static final String KEY_USER_PASSWORD = "password";

        // public constructor for DatabaseConnector
        public DBLocalConnector(Context context)
        {
            // create a new DatabaseOpenHelper
            databaseOpenHelper =
                    new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
        } // end DatabaseConnector constructor

        // open the database connection
        public void open() throws SQLException
        {
            // create or open a database for reading/writing
            database = databaseOpenHelper.getWritableDatabase();
        } // end method open

        // close the database connection
        public void close()
        {
            if (database != null)
                database.close(); // close the database connection
        } // end method close


        private class DatabaseOpenHelper extends SQLiteOpenHelper
        {
            // public constructor
            public DatabaseOpenHelper(Context context, String name,
                                      SQLiteDatabase.CursorFactory factory, int version)
            {
                super(context, name, factory, version);
            } // end DatabaseOpenHelper constructor

            // creates the contacts table when the database is created
            @Override
            public void onCreate(SQLiteDatabase db)
            {
                // query to create a new table named contacts
                String createQuery = String.format("CREATE TABLE %s " +
                                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                " %s INTEGER, %s TEXT, %s CHAR(50), %s CHAR(8));", TABLE_NAME, KEY_USER_ID, KEY_USER_NAME, KEY_USER_EMAIL, KEY_USER_PASSWORD );

                db.execSQL(createQuery); // execute the query
                ContentValues cv = new ContentValues();
                cv.put(KEY_USER_NAME, "");
                cv.put(KEY_USER_EMAIL, "");
                cv.put(KEY_USER_PASSWORD, "");
            } // end method onCreate

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                  int newVersion)
            {
            } // end method onUpgrade
        } // end class DatabaseOpenHelper



    //TODO
    @Override
    public String getUserEmail() {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String USER_EMAIL_QUERY = String.format("SELECT %s FROM %s WHERE id = 1", KEY_USER_EMAIL, TABLE_NAME);

        String email = "";
        Cursor cursor = database.rawQuery(USER_EMAIL_QUERY, null);
        try {
                cursor.moveToFirst();
                email = cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL));
                System.out.println("getting from db email = " + email);
             }
        catch (Exception e){
            Log.e(TAG, "exception while retriveing data in the db");
            e.printStackTrace();
        }
        cursor.close();
        return email;

    }

    @Override
    public String getUserName() {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String USER_NAME_QUERY = String.format("SELECT %s FROM %s WHERE id = 1", KEY_USER_NAME, TABLE_NAME);

        String name = "";
        Cursor cursor = database.rawQuery(USER_NAME_QUERY, null);
        try {
            cursor.moveToFirst();
            name = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME));
            System.out.println("getting from db email = " + name);
        }
        catch (Exception e){
            Log.e(TAG, "exception while retriveing data in the db");
            e.printStackTrace();
        }
        cursor.close();
        return name;
    }

    @Override
    public String getUserPassword() {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String USER_PASSWORD_QUERY = String.format("SELECT %s FROM %s WHERE id = 1", KEY_USER_PASSWORD, TABLE_NAME);

        String password = "";
        Cursor cursor = database.rawQuery(USER_PASSWORD_QUERY, null);
        try {
            cursor.moveToFirst();
            password = cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD));
            System.out.println("getting from db email = " + password);
        }
        catch (Exception e){
            Log.e(TAG, "exception while retriveing data in the db");
            e.printStackTrace();
        }
        cursor.close();
        return password;
    }

    @Override
    public void setUserName(String name) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_NAME,name);

        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.update(TABLE_NAME, cv, "id = 1", null);
        close();

    }


    @Override
    public void setUserEmail(String email) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_EMAIL, email);

        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.update(TABLE_NAME, cv, "id = 1", null);
        close();

    }

    @Override
    public void setUserPassword(String password) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_PASSWORD, password);

        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.update(TABLE_NAME, cv, "id = 1", null);
        close();

    }
}
