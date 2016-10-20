package ar.edu.unc.famaf.redditreader.backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();
    private final String URL_TEMPLATE = "http://www.reddit.com/r/" +".json" + "?after=AFTER";

    public static Backend getInstance() {
        return ourInstance;
    }

    private List<PostModel> mListPostModel;
    private Backend() {
        mListPostModel = new ArrayList<>();
    }

    private class GetTopPostsTask extends AsyncTask<URL, Integer, List<PostModel>> {

        public GetTopPostsTask() {
        
        }
        @Override
        protected List<PostModel> doInBackground(URL... urls) {
            List<PostModel> list = new ArrayList<>();
            URL url = urls[0];
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                String data = is.toString();
                list = parser(data);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            System.out.println("onPostExecute");

            if (isCancelled()) {
                result = null;
            }

        }
    }

    public List<PostModel> getTopPosts() {
        return mListPostModel;
    }

}
