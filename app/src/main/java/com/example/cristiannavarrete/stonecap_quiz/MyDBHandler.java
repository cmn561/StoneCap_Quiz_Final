package com.example.cristiannavarrete.stonecap_quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cristian Navarrete on 11/6/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int TABLE_VERSION = 4;
    private static final String DATABASE_NAME = "ScoreDB.db";

    public static final String TABLE_OUTLINE = "outline";
    public static final String TABLE_FLAG = "flag";
    public static final String TABLE_NICKNAME = "nickname";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";

    private static final String TAG = "Tag";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, TABLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String productQuery1 = "CREATE TABLE " + TABLE_OUTLINE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SCORE + " INTEGER" +
                ");";
        db.execSQL(productQuery1);

        String productQuery2 = "CREATE TABLE " + TABLE_FLAG + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SCORE + " INTEGER" +
                ");";
        db.execSQL(productQuery2);

        String productQuery3 = "CREATE TABLE " + TABLE_NICKNAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SCORE + " INTEGER" +
                ");";
        db.execSQL(productQuery3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTLINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NICKNAME);
        onCreate(db);
    }


    public int addPlayerToOutline(Player player)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, player.getName());
        cv.put(COLUMN_SCORE, player.getScore());
        SQLiteDatabase db = getWritableDatabase();
        long studentID = db.insert(TABLE_OUTLINE, null, cv);
        db.close();
        return (int) studentID;
    }
    public int addPlayerToFlag(Player player)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, player.getName());
        cv.put(COLUMN_SCORE, player.getScore());
        SQLiteDatabase db = getWritableDatabase();
        long studentID = db.insert(TABLE_FLAG, null, cv);
        db.close();
        return (int) studentID;
    }
    public int addPlayerToNickname(Player player)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, player.getName());
        cv.put(COLUMN_SCORE, player.getScore());
        SQLiteDatabase db = getWritableDatabase();
        long studentID = db.insert(TABLE_NICKNAME, null, cv);
        db.close();
        return (int) studentID;
    }

    public void deleteAllRows() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_OUTLINE, null, null);
        db.delete(TABLE_FLAG, null, null);
        db.delete(TABLE_NICKNAME, null, null);
        db.close();
    }

//    public boolean hasUser(String name) {
//        SQLiteDatabase db = getWritableDatabase();
//        String selectString = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " =?";
//
//        // Add the String you are searching by here.
//        // Put it in an array to avoid an unrecognized token error
//        Cursor cursor = db.rawQuery(selectString, new String[] {name});
//
//        boolean hasObject = false;
//        if(cursor.moveToFirst())
//            hasObject = true;
//
//        cursor.close();          // Dont forget to close your cursor
//        db.close();              //AND your Database!
//        return hasObject;
//    }
//
//    //
////        public String databaseToString(){
////            String dbString = "";
////            SQLiteDatabase db = getWritableDatabase();
////            String query = "SELECT * FROM " + TABLE_USERS + " WHERE 1";
////
////
////            Cursor c = db.rawQuery(query, null);
////
////            c.moveToFirst();
////
////            while(!c.isAfterLast()){
////                if(c.getString(c.getColumnIndex("name")) != null){
////                    dbString += c.getString(c.getColumnIndex("level"));
////                    dbString += " ";
////                    dbString += c.getString(c.getColumnIndex("name"));
////                    dbString += " ";
////                    dbString += c.getString(c.getColumnIndex("pass"));
////                    dbString += " ";
////                    dbString += c.getString(c.getColumnIndex("age"));
////                    dbString += "\n";
////                }
////                c.moveToNext();
////
////            }
////            db.close();
////            return dbString;
////        }
////
    public int numRowsOutline()
    {  int r;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_OUTLINE + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        r = c.getCount();
        return r;
    }

    public int numRowsFlag()
    {  int r;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FLAG + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        r = c.getCount();
        return r;
    }

    public int numRowsNickname()
    {  int r;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NICKNAME + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        r = c.getCount();
        return r;
    }
//
//    public int numRowsOfSeller(String seller)
//    {  int r;
//        SQLiteDatabase db = getWritableDatabase();
//        String selectString = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_SELLER + " =?";
//        Cursor c = db.rawQuery(selectString, new String[] {seller});
//        r = c.getCount();
//        return r;
//    }
////
////        public String getRowUserData(String name)
////        {   String s ="";
////            SQLiteDatabase db = getWritableDatabase();
////            String query = "SELECT * FROM " + TABLE_USERS + " WHERE "+ COLUMN_USERNAME + "= "  + name;
////            Cursor c = db.rawQuery(query, null);
////            c.moveToPosition(i);
////            s += c.getString(c.getColumnIndex("name"));
////            s += "    ";
//////        s += c.getString(c.getColumnIndex("pass"));
//////        s += "    ";
////            s += c.getString(c.getColumnIndex("level"));
////
////            return s;
////        }
//
    public Player getRowProductDataOutline(int i)
    {
        Player newPlayer = new Player();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_OUTLINE  + " ORDER BY " + COLUMN_SCORE + " DESC;";
        Cursor c = db.rawQuery(query, null);
        c.moveToPosition(i);
        newPlayer.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
        newPlayer.setScore(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_SCORE))));
        return newPlayer;
    }
    public Player getRowProductDataFlag(int i)
    {
        Player newPlayer = new Player();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FLAG + " WHERE 1 " + "ORDER BY " + COLUMN_SCORE + " DESC;";
        Cursor c = db.rawQuery(query, null);
        c.moveToPosition(i);
        newPlayer.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
        newPlayer.setScore(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_SCORE))));
        return newPlayer;
    }
    public Player getRowProductDataNickname(int i)
    {
        Player newPlayer = new Player();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NICKNAME + " WHERE 1 " + "ORDER BY " + COLUMN_SCORE + " DESC;";
        Cursor c = db.rawQuery(query, null);
        c.moveToPosition(i);
        newPlayer.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
        newPlayer.setScore(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_SCORE))));
        return newPlayer;
    }

//    public Product getRowProductDataOfSeller(int i, String seller) {
//        Product newProduct = new Product();
//        SQLiteDatabase db = getWritableDatabase();
//        String selectString = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_SELLER + " =?";
//        Cursor c = db.rawQuery(selectString, new String[] {seller});
//        c.moveToPosition(i);
//        newProduct.setName(c.getString(c.getColumnIndex(COLUMN_PRODUCTNAME)));
//        newProduct.setDescription(c.getString(c.getColumnIndex(COLUMN_DESCRIPTION)));
//        newProduct.setInvoiceprice(Double.parseDouble(c.getString(c.getColumnIndex(COLUMN_INVOICEPRICE))));
//        newProduct.setSellPrice(Double.parseDouble(c.getString(c.getColumnIndex(COLUMN_SELLPRICE))));
//        newProduct.setQuantity(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_QUANTITY))));
//        newProduct.setSeller(c.getString(c.getColumnIndex(COLUMN_SELLER)));
//
//        return newProduct;
//    }
//
//    public boolean logInMatch(String name, String pass, String buyer) {
//        String passMatch ="";
//        String typeMatch = "";
//        SQLiteDatabase db = getWritableDatabase();
//        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " =?";
//        Cursor c = db.rawQuery(query, new String[] { name });
//        if (c.moveToFirst()) {
//            passMatch += c.getString(c.getColumnIndex(COLUMN_USERPASS));
//            typeMatch += c.getString(c.getColumnIndex(COLUMN_USERTYPE));
//            if (pass.equals(passMatch) && buyer.equals(typeMatch))
//            {
//                c.close();
//                return true;
//            }
//            else
//                return false;
//        }
//        else
//            c.close();
//        return false;
//
//
//    }
//
//
//
////
////        public String getRowUserData_Mod(int i)
////        {   String s ="";
////            SQLiteDatabase db = getWritableDatabase();
////            String query = "SELECT * FROM " + TABLE_USERS + " WHERE 1" + " ORDER BY " + COLUMN_LEVEL + " DESC";
////            Cursor c = db.rawQuery(query, null);
////            c.moveToPosition(i);
////            s += "Name: " + c.getString(c.getColumnIndex("name"));
////            s += "\n";
////            s += "Pass: " + c.getString(c.getColumnIndex("pass"));
////            s += "\n";
////            s += "Level: " + c.getString(c.getColumnIndex("level"));
////            s += "\n";
////            s += "Age: " + c.getString(c.getColumnIndex("age"));
//////        s += "\n";
//////        s += "Gender: " + c.getString(c.getColumnIndex("gender"));
////
////
////
////
////            return s;
////        }

}

