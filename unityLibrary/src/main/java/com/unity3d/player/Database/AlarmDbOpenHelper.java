package com.unity3d.player.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AlarmDbOpenHelper {

    private static final String DATABASE_NAME = "arlarm.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mSQLiteDB;
    private DatabaseHelper mDBHelper;
    private Context mContext;

    private class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(AlarmDatabase.AlarmDB._CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public AlarmDbOpenHelper(Context mContext) {
        this.mContext = mContext;
    }

    public AlarmDbOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mSQLiteDB = mDBHelper.getWritableDatabase();
        return this;
    }
    public void create(){
        mDBHelper.onCreate(mSQLiteDB);
    }
    public void close(){
        mSQLiteDB.close();
    }



    public static long insertColumn(String P_alarmTime, String P_weekofday, int P_gameType, int P_IsSuper, int P_IsActive, int P_ActivateNum, String P_Content){
        ContentValues values = new ContentValues();
        //values.put(AlarmDatabase.AlarmDB.P_id, P_id);
        values.put(AlarmDatabase.AlarmDB.P_alarmTime, P_alarmTime);
        values.put(AlarmDatabase.AlarmDB.P_weekofday, P_weekofday);
        values.put(AlarmDatabase.AlarmDB.P_gameType, P_gameType);
        values.put(AlarmDatabase.AlarmDB.P_IsSuper, P_IsSuper);
        values.put(AlarmDatabase.AlarmDB.P_IsActive, P_IsActive);
        values.put(AlarmDatabase.AlarmDB.P_ActivateNum, P_ActivateNum);
        values.put(AlarmDatabase.AlarmDB.P_Content, P_Content);
        return mSQLiteDB.insert(AlarmDatabase.AlarmDB.TABLE_NAME, null, values);
    }

    public Cursor selectColumns(){
        return mSQLiteDB.query(AlarmDatabase.AlarmDB.TABLE_NAME, null, null, null, null, null, null);
    }

    public void deleteAllColumns() {
        mSQLiteDB.delete(AlarmDatabase.AlarmDB.TABLE_NAME, null, null);
    }

    public boolean deleteColumn(long id){
        return mSQLiteDB.delete(AlarmDatabase.AlarmDB.TABLE_NAME, "P_id="+id, null) > 0;
    }

}
