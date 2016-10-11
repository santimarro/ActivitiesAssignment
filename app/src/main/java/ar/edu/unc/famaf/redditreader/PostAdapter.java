package ar.edu.unc.famaf.redditreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        int position;
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
            holder = new ViewHolder();
            holder.titulo = (TextView) convertView.findViewById(R.id.title);
            holder.subreddit = (TextView) convertView.findViewById(R.id.subreddit);
            holder.comments = (TextView) convertView.findViewById(R.id.cantidad_comentarios);
            holder.date = (TextView) convertView.findViewById(R.id.horas);
            holder.author = (TextView) convertView.findViewById(R.id.autor);
            holder.imagen = (ImageView) convertView.findViewById(R.id.thumbnail);
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
        holder.imagen.setImageResource(p.getmImage());

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mListPostModel.isEmpty();
    }
}
