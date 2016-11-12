package ar.edu.unc.famaf.redditreader.backend;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by smarro on 11/10/16.
 */

public interface PostsIteratorListener {
    void nextPosts(List<PostModel> posts);
    void setAdapter(List<PostModel> posts);
}
