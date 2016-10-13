package ar.edu.unc.famaf.redditreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.prefs.BackingStoreException;

import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * Created by smarro on 9/29/16.
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
        int position;
    }

    private class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {

        private Context mContext;
        private ImageView mImageView;
        private ProgressBar mProgressBar;

        public DownloadImageAsyncTask(Context context, ImageView rootView, ProgressBar progressBar) {
            this.mContext = context;
            this.mImageView = rootView;
            this.mProgressBar = progressBar;
            mProgressBar.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);
        }
        @Override
        protected Bitmap doInBackground(URL... urls) {

            URL url = urls[0];
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
            mImageView.setVisibility(View.VISIBLE);
            mImageView.setImageBitmap(result);
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

        DownloadImageAsyncTask dwnAsyncTask = new DownloadImageAsyncTask(getContext(), holder.imagen, holder.progress);
        dwnAsyncTask.execute(urlArray);

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mListPostModel.isEmpty();
    }
}
