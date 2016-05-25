package uw.edu.tcss450.team6.cryptxt.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jon on 5/24/2016.
 */
public class CryptxtDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Cryptxt.DB";

    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;


    public CryptxtDB(Context context) {
        mDBHelper = new DBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    /**
     * Inserts the receiver into the local sqlite table. Returns true if successful, false otherwise.
     * @param receiver
     * @return true or false
     */
    public boolean insertReceiver(String receiver) {

        mSQLiteDatabase.execSQL("delete from "+ RECEIVER_TABLE);
        ContentValues contentValues = new ContentValues();
        contentValues.put("receiver", receiver);

        long rowId = mSQLiteDatabase.insert("Receiver", null, contentValues);
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    private static final String RECEIVER_TABLE = "Receiver";

    /**
     * Returns the list of courses from the local Course table.
     * @return list
     */
    public String getReceiver() {

        String[] columns = {
                "receiver"
        };

        String receiver = "";

        Cursor c = mSQLiteDatabase.query(
                RECEIVER_TABLE,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        for (int i=0; i<c.getCount(); i++) {
            receiver = c.getString(0);
        }
        return receiver;
    }

    class DBHelper extends SQLiteOpenHelper {

        private static final String CREATE_RECEIVER_SQL =
                "CREATE TABLE IF NOT EXISTS Receiver "
                        + "(receiver TEXT PRIMARY KEY)";

        private static final String DROP_RECEIVER_SQL =
                "DROP TABLE IF EXISTS Receiver";

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_RECEIVER_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_RECEIVER_SQL);
            onCreate(sqLiteDatabase);
        }
    }

}
