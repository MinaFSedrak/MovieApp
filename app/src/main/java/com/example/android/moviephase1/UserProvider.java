package com.example.android.moviephase1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by lenovov on 4/29/2016.
 */
public class UserProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.Mina.provider.User";
    static final String URL = "content://"+PROVIDER_NAME+"/users";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String MDB_POSTER_PATH = "poster_path";
    static final String MDB_OVERVIEW = "overview";
    static final String MDB_RELEASE_DATE = "release_date";
    static final String MDB_ORIGINAL_TITLE = "original_title";
    static final String MDB_ID = "id";
    static final String MDB_VOTE_AVERAGE = "vote_average";


    private SQLiteDatabase db;
    static final String DATABASE_NAME = "UsersDB";
    static final String TABLE_NAME = "USERS";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE="CREATE TABLE "+TABLE_NAME+" ("+MDB_ID+" VARCHAR(225), "+MDB_ORIGINAL_TITLE+" VARCHAR(225), "+MDB_POSTER_PATH
            +" VARCHAR(225), "+MDB_RELEASE_DATE+" VARCHAR(225), "+MDB_OVERVIEW+" VARCHAR(225), "+MDB_VOTE_AVERAGE+" VARCHAR(225));";

    private static class DatabaseHelper extends SQLiteOpenHelper{

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
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();


        return (db == null)?false:true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        if (sortOrder == null || sortOrder ==""){
            sortOrder = MDB_ORIGINAL_TITLE;
        }

        Cursor c = qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME,"",values);
        if(rowID > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        db.close();
        return null;
    }

      @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count =0;
        count = db.delete(TABLE_NAME, selection , selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return  0 ;
    }



}
