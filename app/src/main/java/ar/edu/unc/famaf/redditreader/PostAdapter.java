package ar.edu.unc.famaf.redditreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.prefs.BackingStoreException;

/**
 * Created by smarro on 9/29/16.
 */
public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> mListPostModel;


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
        if(convertView == null) {
            LayoutInflater vi =  (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.post_row, parent);
        }

        PostModel p = mListPostModel.get(position);
        TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
        TextView subreddit = (TextView) convertView.findViewById(R.id.subreddit);
        TextView comments = (TextView) convertView.findViewById(R.id.cantidad_comentarios);
        TextView date = (TextView) convertView.findViewById(R.id.horas);


        titulo.setText(p.getmTitle());
        subreddit.setText(p.getmSubreddit());
        comments.setText(p.getmComments());
        date.setText(p.getmPostDate());

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mListPostModel.isEmpty();
    }
}

