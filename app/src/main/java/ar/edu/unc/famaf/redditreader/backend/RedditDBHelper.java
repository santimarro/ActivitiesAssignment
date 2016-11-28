package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by smarro on 11/3/16.
 */

public class RedditDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "postdb.db";
    public static final String POST_TABLE = "post";
    public static final String POST_TABLE_ID = "_id";
    public static final String POST_TABLE_TITLE = "title";
    public static final String POST_TABLE_AUTHOR = "author";
    public static final String POST_TABLE_SUBREDDIT = "subreddit";
    public static final String POST_TABLE_COMMENTS = "comments";
    public static final String POST_TABLE_POSTDATE = "postdate";
    public static final String POST_TABLE_THUMBNAIL = "thumbnail";
    public static final String POST_TABLE_HINT = "hint";
    public static final String POST_TABLE_THUMBNAIL_B = "thumbnail_b";
    public static final String POST_TABLE_URL = "url";
    public static final int DATABASE_VERSION = 1;

    public RedditDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table "
                + POST_TABLE
                + " ( "
                + POST_TABLE_TITLE + " text not null,"
                + POST_TABLE_AUTHOR + " text not null,"
                + POST_TABLE_SUBREDDIT + " text not null,"
                + POST_TABLE_COMMENTS + " integer not null,"
                + POST_TABLE_POSTDATE + " text not null,"
                + POST_TABLE_THUMBNAIL + " text not null,"
                + POST_TABLE_THUMBNAIL_B + " blob,"
                + POST_TABLE_URL + " text not null,"
                + POST_TABLE_HINT + " text not null,"
                + POST_TABLE_ID + " text not null"
                + " );";
        db.execSQL(createSentence);

        Log.d("DB", "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB", "Database Updated");
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE);
    }

}
