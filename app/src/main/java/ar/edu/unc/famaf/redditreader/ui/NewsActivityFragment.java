package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.GetTopPostsListener;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static java.security.AccessController.getContext;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements GetTopPostsListener {

    ListView lv = null;
    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        Backend backend = Backend.getInstance();
        lv = (ListView) rootView.findViewById(R.id.postLV);
        if(isOnline()) {
            backend.getTopPosts(this, true, getContext());
        }
        else {
            backend.getTopPosts(this, false, getContext());
        }
        return rootView;
    }

    public void getPosts(List<PostModel> postModelLst) {
        PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_row, postModelLst);
        lv.setAdapter(adapter);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}