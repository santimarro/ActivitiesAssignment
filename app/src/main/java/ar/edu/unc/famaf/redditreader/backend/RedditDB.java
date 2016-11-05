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
            values.put(POST_TABLE_TITLE, title);

            long insertId = db.insert(POST_TABLE, null, values);
        }
    }

    public static void dropPosts(SQLiteDatabase db) {

    }

    public static List<PostModel> getDBPosts(SQLiteDatabase db) {
        String[] projection = {
                POST_TABLE_ID,
                POST_TABLE_TITLE,
                POST_TABLE_AUTHOR,
                POST_TABLE_COMMENTS,
                POST_TABLE_SUBREDDIT,
                POST_TABLE_THUMBNAIL,
                POST_TABLE_POSTDATE
        };

        Cursor c = db.query(
                POST_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );


    }


}
