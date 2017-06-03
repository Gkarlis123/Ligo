package karlis_grintals.ligo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlis on 31/05/2017.
 */

public class AppDatabaseAdapter {

    AppHelper helper;
    FileManager fileHelper;

    public AppDatabaseAdapter (Context context) {
        helper = new AppHelper(context);
    }

    public void updateTasks (List<Bundle> taskList) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (Bundle task : taskList) {
            String[] whereArgs = {Integer.toString(task.getInt("checkbox_id"))};
            contentValues.put(helper.STATUS, task.getBoolean("status"));
            db.update(helper.TABLE_NAME_TODO_LIST, contentValues, helper.TASK_ID + "=?", whereArgs);
        }
    }

    public long insertData(String section, String text) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(helper.SECTION_NAME, section);
        contentValues.put(helper.SECTION_INFO, text);

        long id = db.insertWithOnConflict(helper.TABLE_NAME_INFO, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return id;
    }

    public long insertTaskData(String task) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(helper.TASK_NAME, task);
        contentValues.put(helper.STATUS, false);

        long id = db.insert(helper.TABLE_NAME_TODO_LIST, null, contentValues);
        return id;
    }

    public String getData(String section) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {helper.SECTION_NAME, helper.SECTION_INFO};

        Cursor cursor = db.query(helper.TABLE_NAME_INFO, columns, helper.SECTION_NAME + "= '" + section + "'", null, null, null, null);

        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {

            buffer.append(cursor.getString(1));
        }

        return buffer.toString();
    }


    public List<Bundle> getTasks() {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {helper.TASK_NAME, helper.STATUS};


        Cursor cursor = db.query(helper.TABLE_NAME_TODO_LIST, columns, null, null, null, null, null);

        Integer taskNameColumnIndex = cursor.getColumnIndex(helper.TASK_NAME);
        Integer taskStatusColumnIndex = cursor.getColumnIndex(helper.STATUS);

        List<Bundle> bundleList = new ArrayList<Bundle>();


        while (cursor.moveToNext()) {
            Bundle taskBundle = new Bundle();
            Boolean  taskStatus = (Integer.parseInt(cursor.getString(taskStatusColumnIndex)) != 0);

            taskBundle.putBoolean("status", taskStatus);
            taskBundle.putString("task", cursor.getString(taskNameColumnIndex));

            bundleList.add(taskBundle);
        }

        return bundleList;
    }

    public void setDb () {
        SQLiteDatabase db = helper.getWritableDatabase();
    }


    public class AppHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "ligo";
        private static final int DATABASE_VERSION = 13;

        //Table info
        private static final String TABLE_NAME_INFO = "info";

        //Table info attributes
        private static final String INFO_ID = "_id";
        private static final String SECTION_NAME = "section_name";
        private static final String SECTION_INFO = "section_info";

        //Table info query
        private static final String CREATE_TABLE_SECTION_INFO = "CREATE TABLE " + TABLE_NAME_INFO + "("
                + INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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
        private static final String CREATE_TABLE_TODO_LIST = "CREATE TABLE " + TABLE_NAME_TODO_LIST + "("
                + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TASK_NAME + " VARCHAR(255), "
                + TASK_INFO + " VARCHAR(255), "
                + STATUS + " BOOLEAN);";

        private Context context;

        public AppHelper (Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_SECTION_INFO);
                db.execSQL(CREATE_TABLE_TODO_LIST);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INFO);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TODO_LIST);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
