package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static android.R.attr.bitmap;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.*;

/**
 * Created by smarro on 11/3/16.
 */

public class RedditDB {

    private RedditDBHelper db = null;
    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;


    public RedditDB(Context context) {
        db = new RedditDBHelper(context, DATABASE_VERSION);
        writableDB = db.getWritableDatabase();
        readableDB = db.getReadableDatabase();
    }

    public void insert(Listing listing) {

        List<PostModel> postList = listing.getmList();

        ContentValues values = new ContentValues();
        for (PostModel postModel : postList) {
            values.put(POST_TABLE_AUTHOR, postModel.getmAuthor());
            values.put(POST_TABLE_COMMENTS, postModel.getmComments());
            values.put(POST_TABLE_POSTDATE, postModel.getmPostDate());

            if (postModel.getmImage() != null) {
                values.put(POST_TABLE_THUMBNAIL, postModel.getmImage());
            } else {
                values.put(POST_TABLE_THUMBNAIL, "null");
            }

            values.put(POST_TABLE_TITLE, postModel.getmTitle());
            values.put(POST_TABLE_SUBREDDIT, postModel.getmSubreddit());
            values.put(POST_TABLE_ID, postModel.getmId());
            this.writableDB.insert(POST_TABLE, null, values);
        /*
        // Por cada post copio la informacion requerida y la inserto en la tabla
        for (int i = 0; i < postList.size(); i++) {

            String title = postList.get(i).getmTitle();
            String author = postList.get(i).getmAuthor();
            String subreddit = postList.get(i).getmSubreddit();
            int comments = postList.get(i).getmComments();
            String postDate = postList.get(i).getmPostDate();
            String thumbnail = postList.get(i).getmImage();
            String id = postList.get(i).getmId();

            String sql = "INSERT INTO " + POST_TABLE + " ("+ POST_TABLE_TITLE + ", "
                    + POST_TABLE_AUTHOR + ", "
                    + POST_TABLE_SUBREDDIT + ", "
                    + POST_TABLE_COMMENTS + ", "
                    + POST_TABLE_POSTDATE + ", "
                    + POST_TABLE_THUMBNAIL +", "
                    + POST_TABLE_ID + ")"
            + " VALUES ('" + title + "','" + author + "', '" + subreddit + "', '" + comments + "', '" + postDate + "', '" + thumbnail + "', '" + id + "') ";
            writableDB.execSQL(sql);
        */
        }
    }


    public void dropPosts() {
        writableDB.execSQL("delete from "+ POST_TABLE);
    }

    public List<PostModel> getDBPosts(int first) {

        String queryString = "SELECT * FROM " + POST_TABLE + " LIMIT " + Integer.toString(first) + ", 5;";

        Cursor c = readableDB.rawQuery(queryString, null);
        int count = c.getCount();
        c.moveToFirst();
        String title = null;
        String author = null;
        String subreddit = null;
        int comments = 0;
        String postdate = null;
        String thumbnail = null;
        String id = null;

        List<PostModel> plist = new ArrayList<>();
        if (c.moveToFirst()) {
             do {
                title = c.getString(c.getColumnIndexOrThrow(POST_TABLE_TITLE));
                author = c.getString(c.getColumnIndexOrThrow(POST_TABLE_AUTHOR));
                subreddit = c.getString(c.getColumnIndexOrThrow(POST_TABLE_SUBREDDIT));
                comments = c.getInt(c.getColumnIndexOrThrow(POST_TABLE_COMMENTS));
                postdate = c.getString(c.getColumnIndexOrThrow(POST_TABLE_POSTDATE));
                thumbnail = c.getString(c.getColumnIndexOrThrow(POST_TABLE_THUMBNAIL));
                id = c.getString(c.getColumnIndexOrThrow(POST_TABLE_ID));

                PostModel p = new PostModel(id, title, author, subreddit, comments, postdate ,thumbnail);
                plist.add(p);
              } while (c.moveToNext());
        }
        c.close();
        return plist;
    }

    public boolean isEmpty() {
        boolean result = true;
        String selection = "SELECT * FROM " + POST_TABLE;
        Cursor c = readableDB.rawQuery(selection, null);

        if (c.moveToFirst())
            result = false;

        c.close();
        return result;
        }

    public static byte[] getBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
    public static Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void insertImage(String thumbnail, Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(POST_TABLE_THUMBNAIL_B, getBytes(bitmap));
        String whereClause = POST_TABLE_THUMBNAIL + "= '" + thumbnail + "'";
        writableDB.update(POST_TABLE, values, whereClause, null);
    }

    public Bitmap getImage(String thumbnail) {
        Bitmap result = null;
        String whereClause = POST_TABLE_THUMBNAIL + "= '" + thumbnail + "'";
        String query = " SELECT * FROM " + POST_TABLE + " WHERE " + whereClause + ";";
        Cursor c = readableDB.rawQuery(query, null);

        if (c.moveToFirst() && !c.isNull(c.getColumnIndex(POST_TABLE_THUMBNAIL_B))) {
                result = getImage(c.getBlob(c.getColumnIndex(POST_TABLE_THUMBNAIL_B)));
            }
        c.close();
        return result;
    }

}
