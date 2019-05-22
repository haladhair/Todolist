package com.haladhair.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_name = "hala";
    private static final int DB_Var = 1;
    public static final String DB_TABLE = "Task";
   public static final String DB_COLAD = "Taskname";

    public DbHelper(Context context) {
        super(context, DB_name, null, DB_Var);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %S(ID INTEGER PRIMARY KEY AUTOINCREMENT,%S TEXT NOT NULL);", DB_TABLE, DB_COLAD);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXIST %S", DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }

    public void insertNewTask(String task) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_COLAD,task);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLAD + " = ?",new String[]{task});
        db.close();
    }
    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList =new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLAD},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLAD);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }
}
