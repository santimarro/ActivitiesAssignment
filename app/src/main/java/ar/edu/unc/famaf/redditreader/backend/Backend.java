package ar.edu.unc.famaf.redditreader.backend;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private List<PostModel> mListPostModel;
    private Backend() {
        mListPostModel = new ArrayList<>();

        PostModel p1 = new PostModel("pepe loro","ophionn","todayilearned", 1000, "4 hs", "www.goog/algo.jpg");
        mListPostModel.add(p1);

        PostModel p2 = new PostModel("lala","kenny","r/todayilearned",56, "1 hs", "www.goog/algo.jpg");
        mListPostModel.add(p2);


        PostModel p3 = new PostModel("lololo","mark","r/radiohead", 23, "1 hs", "www.goog/algo.jpg");
        mListPostModel.add(p3);

        PostModel p4 = new PostModel("lololo","john","r/radiohead", 23, "1 hs", "www.goog/algo.jpg");
        mListPostModel.add(p3);

        PostModel p5 = new PostModel("lololo","turk","r/radiohead", 23, "1 hs", "www.goog/algo.jpg");
        mListPostModel.add(p3);
    }

    public List<PostModel> getTopPosts() {
        return mListPostModel;
    }

}
