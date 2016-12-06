package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.model.IntBoxer;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static android.R.attr.key;
import static android.R.attr.type;
import static android.R.id.empty;
import static ar.edu.unc.famaf.redditreader.backend.RedditDBHelper.DATABASE_VERSION;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }
    private List<PostModel> mListPostModel;
    private Listing mListing;
    private int currentPost = -1;
    private String top_url = "https://www.reddit.com/top/.json?limit=50";
    private String newPosts_url = "https://www.reddit.com/new/.json?limit=50";
    private String hot_url = "https://www.reddit.com/hot/.json?limit=50";
    private String hot = "hot";
    private String top = "top";
    private String newPosts = "new";

    private Backend() {
        mListing = new Listing(null);
        mListPostModel = new ArrayList<>();
    }

    private class GetTopPostsTask extends AsyncTask<URL, Integer, Listing> {

        public GetTopPostsTask() {

        }
        @Override
        protected Listing doInBackground(URL... params) {
            Listing lst= null;
            HttpURLConnection connection;
            try {

                connection = (HttpURLConnection) params[0].openConnection();
                connection.setRequestMethod("GET");
                InputStream is = connection.getInputStream();

                lst = new Parser().readJsonStream(is);
                mListPostModel.addAll(lst.getmList());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return lst;
        }
    }

    public List<PostModel> getList () {
        return mListPostModel;
    }

    public void getTopPosts(final PostsIteratorListener listener, boolean Internet, final Context context, String tab) {
        URL url = null;
        String key = hot;
        try {
            switch (tab) {
                case "top":
                    url = new URL(top_url);
                    key = top;
                    break;
                case "new":
                    url = new URL(newPosts_url);
                    key = newPosts;
                    break;
                case "hot":
                    url = new URL(hot_url);
                    key = hot;
                    break;
                default:
                    url = new URL(hot_url);
                    key = hot;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final String db_key = key;
        final RedditDB db = new RedditDB(context);
        final boolean setAdapter = true;
        if (Internet) {
            new GetTopPostsTask() {
                @Override
                protected void onPostExecute(Listing listing) {;
                    // First we drop all the stored posts
                    db.dropPosts(db_key);
                    // Then we store the new ones
                    db.insert(listing, db_key);
                    listener.setAdapter(db.getDBPosts(0, db_key));
                    currentPost = 5;
                }
            }.execute(url);
        } else {
            boolean empty = db.isEmpty(db_key);
            if(!empty) {
                // Show the last 50 posts already stored.
                listener.setAdapter(db.getDBPosts(0, db_key));
                currentPost = 5;
            } else {
                // ERROR
            }
        }


    }

    public void getNextPosts(final PostsIteratorListener Ilistener, boolean Internet, final Context context, String tab, boolean newFragment) {

        if(newFragment) {
            currentPost = -1;
        }
        if (this.currentPost == -1) {
            getTopPosts(Ilistener, Internet, context, tab);
        } else {
            final RedditDB db = new RedditDB(context);
            Ilistener.nextPosts(db.getDBPosts(currentPost, tab));
            currentPost += 5;
        }
    }
}