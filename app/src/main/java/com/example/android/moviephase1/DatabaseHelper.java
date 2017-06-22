package com.example.android.moviephase1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovov on 12/4/2016.
 */

/*public class DataBaseHelper extends SQLiteOpenHelper {
    private static final  String DATABASE_NAME="movie.db";
    Context context;
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="favourite_table";
    private static final String COL_1="image";
    private static final String COL_2="id";
    private static final String COL_3="tittle";
    private static final String COL_4="date";
    private static final String COL_5="over_view";
    private static final String COL_6="rate";
    private static DataBaseHelper instance = null;


    private static final String CREATE_DATABASE_TABLE = "CREATE TABLE"+TABLE_NAME+" ( "+ COL_1 +" VARCHAR(225), "+ COL_2 + " VARCHAR(225), "+ COL_3 + " VARCHAR(225), "+COL_4 +" VARCHAR(225), " + COL_5 + " VARCHAR(225), "+ COL_6 +" VARCHAR(225)); ";

    public DataBaseHelper(Context context) {

        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    public static DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + COL_2 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + COL_3 + " VARCHAR(255) NOT NULL, "
                            + COL_4 + " VARCHAR(255) NOT NULL, "
                            + COL_5 + " TEXT NOT NULL, "
                            + COL_1+ " TEXT NOT NULL, "
                            + COL_6+ " REAL NOT NULL" +
                            ");"

            );
            Toast.makeText(context, "Table created", Toast.LENGTH_LONG).show();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(MovieInfo movieInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,movieInfo.getId());
        contentValues.put(COL_2,movieInfo.getTittle());
        contentValues.put(COL_3,movieInfo.getDate());
        contentValues.put(COL_4,movieInfo.getOverview());
        contentValues.put(COL_5,movieInfo.getImageurl());
        contentValues.put(COL_6,movieInfo.getAverage());

        long result =  db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if (result == -1)
        {
            return false;

        }
        else{
            return true;
        }

    }

    public Cursor retrieveRow()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM" +TABLE_NAME,null);
        return  cursor;
    }
}*/
public class DatabaseHelper extends SQLiteOpenHelper {

    static final String MDB_POSTER_PATH = "poster_path";
    static final String MDB_OVERVIEW = "overview";
    static final String MDB_RELEASE_DATE = "release_date";
    static final String MDB_ORIGINAL_TITLE = "original_title";
    static final String MDB_ID = "id";
    static final String MDB_VOTE_AVERAGE = "vote_average";

    static final String DATABASE_NAME = "UsersDB";
    static final String TABLE_NAME = "USERS";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE="CREATE TABLE "+TABLE_NAME+" ("+MDB_ID+" VARCHAR(225), "+MDB_ORIGINAL_TITLE+" VARCHAR(225), "+MDB_POSTER_PATH
            +" VARCHAR(225), "+MDB_RELEASE_DATE+" VARCHAR(225), "+MDB_OVERVIEW+" VARCHAR(225), "+MDB_VOTE_AVERAGE+" VARCHAR(225));";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
    }


    public boolean insertRow(MovieInfo movieInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MDB_ID,movieInfo.getId());
        values.put(MDB_ORIGINAL_TITLE,movieInfo.getTitle());
        values.put(MDB_RELEASE_DATE,movieInfo.getReleaseDate());
        values.put(MDB_OVERVIEW,movieInfo.getOverview());
        values.put(MDB_POSTER_PATH,movieInfo.getImageURL());
        values.put(MDB_VOTE_AVERAGE, movieInfo.getRating());

        long result =  db.insert(TABLE_NAME,null,values);
        db.close();
        if (result == -1)
        {
            return false;

        }
        else{
            return true;
        }

    }



    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor source = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return source;

    }


}
