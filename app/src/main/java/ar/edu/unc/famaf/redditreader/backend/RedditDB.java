package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.*;

/**
 * Created by smarro on 11/3/16.
 */

public class RedditDB {

    public static void insert(SQLiteDatabase db, Listing listing) {

        List<PostModel> postList = listing.getmList();
        // Por cada post copio la informacion requerida y la inserto en la tabla
        for (int i = 0; i < postList.size(); i++) {

            ContentValues values = new ContentValues();

            String title = postList.get(i).getmTitle();
            String author = postList.get(i).getmAuthor();
            int comments = postList.get(i).getmComments();
            String thumbnail = postList.get(i).getmImage();
            String subreddit = postList.get(i).getmSubreddit();
            String postDate = postList.get(i).getmPostDate();
            String id = postList.get(i).getmId();

            values.put(POST_TABLE_TITLE, title);
            values.put(POST_TABLE_AUTHOR, author);
            values.put(POST_TABLE_COMMENTS, comments);
            values.put(POST_TABLE_THUMBNAIL, thumbnail);
            values.put(POST_TABLE_SUBREDDIT, subreddit);
            values.put(POST_TABLE_POSTDATE, postDate);
            values.put(POST_TABLE_ID, id);

            long insertId = db.insert(POST_TABLE, null, values);
        }
    }

    public static void dropPosts(SQLiteDatabase db) {
        db.execSQL("delete from "+ POST_TABLE);
    }

    public static List<PostModel> getDBPosts(SQLiteDatabase db) {

        String queryString = "SELECT * FROM " + POST_TABLE;

        Cursor c = db.rawQuery(queryString, null);
        int count = c.getCount();
        c.moveToFirst();
        String title = null;
        String author = null;
        String subreddit = null;
        int comments = 0;
        String postdate = null;
        String thumbnail = null;
        String id = null;

        List<PostModel> plist = null;
        while (c.isAfterLast()) {
            title = c.getString(0);
            author = c.getString(1);
            subreddit = c.getString(2);
            comments = c.getInt(3);
            postdate = c.getString(4);
            thumbnail = c.getString(5);
            id = c.getString(6);

            PostModel p = new PostModel(id, title, author, subreddit, comments, postdate ,thumbnail);
            plist.add(p);
            c.moveToNext();
        }
        return plist;
    }


}
