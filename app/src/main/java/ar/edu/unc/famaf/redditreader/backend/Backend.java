package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;
import android.widget.Adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }
    private List<PostModel> mListPostModel;
    private Listing mListing;
    private PostAdapter mAdapter = null;

    private Backend() {
        mListing = new Listing(null);
        mListPostModel = new ArrayList<>();
    }

    private class GetTopPostsTask extends AsyncTask<URL, Integer, Listing> {

        public GetTopPostsTask() {

        }
        @Override
        protected Listing doInBackground(URL... urls) {
            Listing lst_tmp = new Listing(null, null, null);
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) new URL("http://www.reddit.com/top/.json").openConnection();
                connection.setRequestMethod("GET");
                InputStream is = connection.getInputStream();

                Listing lst = new Parser().readJsonStream(is);
                mListPostModel.addAll(lst.getmList());
                mAdapter.notifyDataSetChanged();
                return lst;

            } catch (IOException e) {
                e.printStackTrace();
                return lst_tmp;
            }
        }
    }

    public List<PostModel> getList () {
        return mListPostModel;
    }

    public void setAdapter (PostAdapter adapter) {
        mAdapter = adapter;
    }
    public void getTopPosts() {

        GetTopPostsTask task = new GetTopPostsTask();
        URL[] url = null;
        task.execute(url);
        // return mListPostModel;
    }
}