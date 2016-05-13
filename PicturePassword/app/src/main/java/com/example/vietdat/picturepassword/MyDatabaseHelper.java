package com.example.vietdat.picturepassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "picturepassword";


    // Tên bảng: Note.
    private static final String TABLE_NAME = "account";

    private static final String COLUMN_ID ="Account_Id";
    private static final String COLUMN_X ="x";
    private static final String COLUMN_Y = "y";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo các bảng.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_X + " FLOAT,"
                + COLUMN_Y + " FLOAT" + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Và tạo lại.
        onCreate(db);
    }



    public void addToado(float x, float y) {
        Log.i(TAG, "MyDatabaseHelper.addX_Y ... ");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_X, x);
        values.put(COLUMN_Y, y);


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_NAME, null, values);


        // Đóng kết nối database.
        db.close();
    }


    public void print(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from " + TABLE_NAME, null);
        int cnt = cursor.getCount();
        System.out.println("so hang db la" + cnt);
        cursor.close();

    }

    public String[] getToaDo(int id) {
        Log.i(TAG, "MyDatabaseHelper.getToaDo ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID,
                        COLUMN_X, COLUMN_Y}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String[] lst = new String[2];
        lst[0] = cursor.getString(1);
        lst[1] = cursor.getString(2);

        System.out.print("x = " + lst[0] + ", y = " + lst[1]);
        // return note
        return lst;
    }

    public void getAllToaDo() {
        Log.i(TAG, "MyDatabaseHelper.get all ToaDo ... ");

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor  cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
                do{
                    System.out.println(cursor.getString(1));
                }while (cursor.moveToNext());
        }

    }

    public String[] getLastRecordOfTable(){
        Log.i(TAG, "MyDatabaseHelper get last record Toado ... ");

        String[] str = new String[2];

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        str[0] = cursor.getString(1);
        str[1] = cursor.getString(2);

        System.err.println("x = " +str[0]);
        System.err.println("y = " + str[1]);

        return str;
    }
    public void deleteToaDo(){
        Log.i(TAG, "MyDatabaseHelper delete Toado ... ");

        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("delete from "+ TABLE_NAME);
    }


    public int update(float x, float y) {
        Log.i(TAG, "MyDatabaseHelper.update ... " );

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_X, x);
        values.put(COLUMN_Y, y);

        return 0;
//        // updating row
//        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
//                new String[]{String.valueOf(note.getNoteId())});
    }

}