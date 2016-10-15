package ar.edu.unc.famaf.redditreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * Created by smarro on 9/29/16.
 */

/*
 * Full disclosure: Se utilizó como ayuda las filminas vistas en clase y el siguiente tutorial
 * para solucionar las concurrencias.
 * http://android-developers.blogspot.com.ar/2010/07/multithreading-for-performance.html
 */
public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> mListPostModel;

    static class ViewHolder {
        TextView titulo;
        TextView subreddit;
        TextView comments;
        TextView date;
        TextView author;
        ImageView imagen;
        ProgressBar progress;
    }

    private class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {

        private ImageView mImageView;
        private ProgressBar mProgressBar;
        private String mUrl;
        private final WeakReference<ImageView> imageViewReference;

        public DownloadImageAsyncTask(ImageView imageView, ProgressBar progressBar) {
            this.mImageView = imageView;
            this.mProgressBar = progressBar;
            mProgressBar.setVisibility(View.VISIBLE);
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

            mProgressBar.setVisibility(View.GONE);

            if (isCancelled()) {
                result = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                DownloadImageAsyncTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
                // Change bitmap only if this process is still associated with it
                if (this == bitmapDownloaderTask) {
                    imageView.setImageBitmap(result);
                    mImageView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public PostAdapter(Context context, int resource, List<PostModel> lst) {
        super(context, resource);
        mListPostModel = lst;

    }

    @Override
    public int getCount() {
        return mListPostModel.size();
    }

    @Override
    public PostModel getItem(int position) {
        return mListPostModel.get(position);
    }

    @Override
    public int getPosition(PostModel item) {
        return mListPostModel.indexOf(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            LayoutInflater vi =  (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.post_row, parent, false);

        }
        if(convertView.getTag() == null) {
            holder = new ViewHolder();
            holder.titulo = (TextView) convertView.findViewById(R.id.title);
            holder.subreddit = (TextView) convertView.findViewById(R.id.subreddit);
            holder.comments = (TextView) convertView.findViewById(R.id.cantidad_comentarios);
            holder.date = (TextView) convertView.findViewById(R.id.horas);
            holder.author = (TextView) convertView.findViewById(R.id.autor);
            holder.imagen = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.progress = (ProgressBar) convertView.findViewById(R.id.progress_bar);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PostModel p = mListPostModel.get(position);

        holder.titulo.setText(p.getmTitle());
        holder.subreddit.setText(p.getmSubreddit());
        holder.comments.setText(String.valueOf(p.getmComments()));
        holder.date.setText(p.getmPostDate());
        holder.author.setText(p.getmAuthor());

        URL[] urlArray = new URL[1];
        try {
            urlArray[0] = new URL(p.getmImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        download(urlArray, holder.imagen, holder.progress);

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mListPostModel.isEmpty();
    }

    // Metodos encargado de descargar bitmap y manejar concurrencias.
    public void download(URL[] url, ImageView imageView, ProgressBar progressBar) {
        if (cancelPotentialDownload(url[0].toString(), imageView)) {
            PostAdapter.DownloadImageAsyncTask task = new PostAdapter.DownloadImageAsyncTask(imageView, progressBar);
            DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
            imageView.setImageDrawable(downloadedDrawable);
            task.execute(url);
        }
    }
    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        DownloadImageAsyncTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.mUrl;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    private static DownloadImageAsyncTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<DownloadImageAsyncTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(DownloadImageAsyncTask bitmapDownloaderTask) {
            super(Color.BLACK);
            bitmapDownloaderTaskReference = new WeakReference<DownloadImageAsyncTask>(bitmapDownloaderTask);
        }

        public DownloadImageAsyncTask getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }

}
