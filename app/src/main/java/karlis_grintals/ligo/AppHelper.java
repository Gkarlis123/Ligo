package karlis_grintals.ligo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by karlis on 31/05/2017.
 */

public class AppDatabaseAdapter{

    AppHelper helper;

    public AppDatabaseAdapter (Context context) {
        helper = new AppHelper(context);
    }

    public long insertData(String text) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(helper.INFO_ID, 1);
        contentValues.put(helper.TABLE_TEXT, text);

        long id = db.insertWithOnConflict(helper.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {helper.INFO_ID, helper.TABLE_TEXT};

        Cursor cursor = db.query(helper.TABLE_NAME, columns, helper.INFO_ID + "= 1", null, null, null, null);

        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {

            buffer.append(cursor.getString(1));
        }



        return buffer.toString();
    }

    public class AppHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "ligo";
        private static final int DATABASE_VESRION = 1;

        //Table info
        private static final String TABLE_NAME_INFO = "info";

        //Table info attributes
        private static final String INFO_ID = "_id";
        private static final String SECTION_NAME = "section_name";
        private static final String SECTION_INFO = "section_info";

        //Table info query
        private static final String CREATE_TABLE_SECTION_INFO = "CREATE TABLE" + TABLE_NAME_INFO + "("
                + INFO_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SECTION_NAME + " VARCHAR(255), "
                + SECTION_INFO + " TEXT);";

        //Table todo_list
        private static final String TABLE_NAME_TODO_LIST = "todo_list";

        //Table todo_list attributes
        private static final String TASK_ID = "_id";
        private static final String TASK_NAME = "task_name";
        private static final String STATUS = "status";
        private static final String TASK_INFO = "task_info";

        //Table todo_list query
        private static final String CREATE_TABLE_TODO_LIST = "CREATE TABLE" + TABLE_NAME_TODO_LIST + "("
                + TASK_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TASK_NAME + " VARCHAR(255), "
                + TASK_INFO + " VARCHAR(255), "
                + STATUS + "BOOLEAN);";

        private Context context;

        public AppHelper (Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VESRION);
            this.context = context;
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_SECTION_INFO);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS;");
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
