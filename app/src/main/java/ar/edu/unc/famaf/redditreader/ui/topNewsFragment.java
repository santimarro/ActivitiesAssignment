package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.backend.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class topNewsFragment extends Fragment implements PostsIteratorListener {
    List<PostModel> postsList = null;
    ListView lv = null;
    PostAdapter adapter = null;
    Activity a;
    boolean newFragment = true;

    public topNewsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            a = (Activity) context;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_news, container, false);
        lv = (ListView) rootView.findViewById(R.id.postLV);
        Backend.getInstance().getNextPosts(this, isOnline(), getContext(), "top", newFragment);
        lv.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {

                Backend.getInstance().getNextPosts(topNewsFragment.this, isOnline(), getContext(), "top", !newFragment);
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((frontpageActivity) a).onPostItemPicked(postsList.get(i));
            }
        });
        return rootView;
    }

    public void setAdapter(List<PostModel> postList) {
        postsList = postList;
        if(getActivity() != null) {
            adapter = new PostAdapter(getActivity(), R.layout.post_row, postsList);
            lv.setAdapter(adapter);
        }
    }

    public void nextPosts(List<PostModel> postModelLst) {
        postsList.addAll(postModelLst);
        adapter.notifyDataSetChanged();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}