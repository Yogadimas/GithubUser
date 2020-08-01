package com.yogadimas.githubuser.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.TABLE_GITHUB_NAME;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_GITHUB_NAME = "name_github_user_db";
    private static final int DATABASE_GITHUB_VERSION = 1;

    private static final String SQL_CREATE_TABLE_GITHUB = String.format(
            "CREATE TABLE %s" +
                    "(%s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL)",
            TABLE_GITHUB_NAME,
            DatabaseContract.ThisGithubColumns.AVATAR_URL,
            DatabaseContract.ThisGithubColumns.LOGIN,
            DatabaseContract.ThisGithubColumns.USER_ID,
            DatabaseContract.ThisGithubColumns.HTML_URL
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_GITHUB_NAME, null, DATABASE_GITHUB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_GITHUB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GITHUB_NAME);
        onCreate(database);
    }
}
