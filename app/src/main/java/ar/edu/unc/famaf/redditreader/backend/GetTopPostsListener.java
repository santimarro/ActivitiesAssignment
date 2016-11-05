package ar.edu.unc.famaf.redditreader.backend;

/**
 * Created by Santi on 29/10/2016.
 */

import ar.edu.unc.famaf.redditreader.model.PostModel;
import java.util.List;


public interface GetTopPostsListener {
    void getPosts(List<PostModel> postModels);
}
