package cmu.sv.flubber.ihere.dbLayout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class DBLocalConnector implements DBLocalConnectorInterface {
        // tag
        private static final String TAG = "DatabaseConnector";

        // database info
        public static final String DATABASE_NAME = "StudentRecord";
        private SQLiteDatabase database; // database object
        private DatabaseOpenHelper databaseOpenHelper; // database helper

        // table names
        public static final String STU_QUIZ_TABLE = "studentQuizes";

        // studentQuizes columns
        public static final String KEY_STU_ID = "stuId";
        public static final String KEY_Q1_SCORE = "q1";
        public static final String KEY_Q2_SCORE = "q2";
        public static final String KEY_Q3_SCORE = "q3";
        public static final String KEY_Q4_SCORE = "q4";
        public static final String KEY_Q5_SCORE = "q5";

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
                                " %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER);",
                        STU_QUIZ_TABLE, KEY_STU_ID, KEY_Q1_SCORE, KEY_Q2_SCORE,
                        KEY_Q3_SCORE, KEY_Q4_SCORE, KEY_Q5_SCORE);

                db.execSQL(createQuery); // execute the query
            } // end method onCreate

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                  int newVersion)
            {
            } // end method onUpgrade
        } // end class DatabaseOpenHelper



    @Override
    public String getUserEmail() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public void setUserName(String name) {

    }

    @Override
    public void setUserEmail(String email) {

    }

    @Override
    public void updateuserEmail(String email) {

    }
}
