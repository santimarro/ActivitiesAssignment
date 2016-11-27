package ar.edu.unc.famaf.redditreader.ui;

/**
 * Created by Santi on 27/11/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.unc.famaf.redditreader.R;

public class NewsDetailActivityFragment extends Fragment {

    public NewsDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }
}
