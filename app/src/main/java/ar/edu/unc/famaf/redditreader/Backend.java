package ar.edu.unc.famaf.redditreader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smarro on 9/29/16.
 */
public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private List<PostModel> mListPostModel;
    private Backend() {
        mListPostModel = new ArrayList<>();

        PostModel p1 = new PostModel("pepe loro","pepito se comio un lorito","todayilearned", 1000, "4 hs", "www.goog/algo.jpg");
        mListPostModel.add(p1);

        PostModel p2 = new PostModel("lala","lelelel","r/todayilearned",56, "1 hs", "www.goog/algo.jpg");
        mListPostModel.add(p2);


        PostModel p3 = new PostModel("lololo","asdpiasndsaid","r/radiohead", 23, "1 hs", "www.goog/algo.jpg");
        mListPostModel.add(p3);
    }

    public List<PostModel> getTopPosts() {
        return mListPostModel;
    }
}
