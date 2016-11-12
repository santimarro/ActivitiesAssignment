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
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

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

    public void getTopPosts(final PostsIteratorListener listener, boolean Internet, final Context context) {
        URL url = null;
        try {
            url = new URL("https://www.reddit.com/top/.json?limit=50");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        final RedditDB db = new RedditDB(context);
        final boolean setAdapter = true;
        if (Internet) {
            new GetTopPostsTask() {
                @Override
                protected void onPostExecute(Listing listing) {;
                    // First we drop all the stored posts
                    db.dropPosts();
                    // Then we store the new ones
                    db.insert(listing);
                    listener.setAdapter(db.getDBPosts(0, 5));
                    currentPost = 5;
                }
            }.execute(url);
        } else {
            boolean empty = db.isEmpty();
            if(!empty) {
                // Show the last 50 posts already stored.
                listener.setAdapter(db.getDBPosts(0, 5));
                currentPost = 5;
            } else {
                // ERROR
            }
        }


    }

    public void getNextPosts(final PostsIteratorListener Ilistener, boolean Internet, final Context context) {
        if (this.currentPost == -1) {
            getTopPosts(Ilistener, Internet, context);
        } else {
            final RedditDB db = new RedditDB(context);
            Ilistener.nextPosts(db.getDBPosts(currentPost, 5));
            currentPost += 5;
            /*if(currentPost != 50) {
                currentPost = (currentPost + 5) % 50;
            } else {
                currentPost = -1;
            }*/

        }
    }
}
