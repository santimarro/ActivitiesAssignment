package ar.edu.unc.famaf.redditreader.ui;

/**
 * Created by Santi on 27/11/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDB;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static android.R.attr.bitmap;

public class NewsDetailActivityFragment extends Fragment {
    String POST_DETAIL = "post_detail";
    public static final String URL = "url";
    private ImageView preview;

    public NewsDetailActivityFragment() {
    }

    private class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {

        private ImageView mImageView;
        private String mUrl;
        private final WeakReference<ImageView> imageViewReference;

        public  DownloadImageAsyncTask(ImageView imageView) {
            this.mImageView = imageView;
            mImageView.setVisibility(View.GONE);
            imageViewReference = new WeakReference<ImageView>(imageView);
        }
        @Override
        protected Bitmap doInBackground(URL... urls) {

            URL url = urls[0];
            mUrl = url.toString();
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            System.out.println("onPostExecute");
            if (isCancelled()) {
                result = null;
            }
            if(result != null) {
                preview.setImageBitmap(result);
                preview.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_news_detail_fragment, container, false);
        PostModel post = (PostModel) this.getArguments().getSerializable(POST_DETAIL);

        // Setear el xml del fragment con la info del post.
        TextView title = (TextView) rootView.findViewById(R.id.detail_title);
        title.setText(post.getmTitle());
        TextView subreddit = (TextView) rootView.findViewById(R.id.detail_subreddit);
        subreddit.setText("r/" + post.getmSubreddit());
        TextView author = (TextView) rootView.findViewById(R.id.detail_author);
        author.setText(post.getmAuthor());
        TextView date = (TextView) rootView.findViewById(R.id.detail_date);
        date.setText(post.getmPostDate());

        // Seteamos la preview
        String postHint = post.getmPostHint();
        String thumbnail;
        preview = (ImageView) rootView.findViewById(R.id.detail_preview);
        URL[] urlArray = new URL[1];
        boolean preview_needed = false;

        if(postHint.equals("image")) {
            thumbnail = post.getmUrl();
        } else {
            thumbnail = post.getmImage();
            preview_needed = true;
        }

        try {
            urlArray[0] = new URL(thumbnail);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(thumbnail != null) {
            Bitmap bitmap = null;
            if(preview_needed) {
                RedditDB db = new RedditDB(getContext());
                bitmap = db.getImage(thumbnail);
            }

            if (bitmap == null) {
                if(urlArray[0] != null && isOnline()) {
                    URL url = urlArray[0];
                    NewsDetailActivityFragment.DownloadImageAsyncTask task = new NewsDetailActivityFragment.DownloadImageAsyncTask(preview);
                    task.execute(url);
                }
                else {
                    preview.setImageResource(R.mipmap.ic_launcher);
                    preview.setVisibility(View.VISIBLE);
                }

            } else {
                preview.setImageBitmap(bitmap);
                preview.setVisibility(View.VISIBLE);
            }
        }

        // Seteamos el boton que abrira la webView del link del post
        Button button = (Button) rootView.findViewById(R.id.detail_link_button);
        final String postUrl = post.getmUrl();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), webViewActivity.class);
                intent.putExtra(URL, postUrl);
                startActivity(intent);
            }
        });
        if(!preview_needed)
            button.setVisibility(View.GONE);

        return rootView;

    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
