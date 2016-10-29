package ar.edu.unc.famaf.redditreader.model;

import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by smarro on 10/27/16.
 */

public class Listing {

    private List<PostModel> mList;
    private String mAfter;
    private String mBefore;

    public Listing(List<PostModel> list, String after, String before) {
        mBefore = before;
        mAfter = after;
        mList = list;
    }

    public String getmBefore() {
        return mBefore;
    }

    public String getmAfter() {
        return mAfter;
    }

    public List<PostModel> getmList() {
        return mList;
    }

    public Listing(String before) {
        mBefore = before;
    }
}

