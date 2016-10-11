package ar.edu.unc.famaf.redditreader.backend;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private List<PostModel> mListPostModel;
    private Backend() {
        mListPostModel = new ArrayList<>();

        PostModel p1 = new PostModel("This is not concrete..this is New Delhi","ophionn","r/todayilearned", 1000, "4 hs", R.drawable.bus);
        mListPostModel.add(p1);

        PostModel p2 = new PostModel("It is true","kenny","r/todayilearned",56, "1 hs", R.drawable.dog);
        mListPostModel.add(p2);

        PostModel p3 = new PostModel("My mom cut into her carrot and found a smiley face.","mark","r/radiohead", 23, "1 hs", R.drawable.smile);
        mListPostModel.add(p3);

        PostModel p4 = new PostModel("First smile ever","john","r/funny", 23, "1 hs", R.drawable.smile);
        mListPostModel.add(p4);

        PostModel p5 = new PostModel("The neighbors dog loves me","turk","r/aww", 23, "1 hs", R.drawable.dog);
        mListPostModel.add(p5);
    }

    public List<PostModel> getTopPosts() {
        return mListPostModel;
    }

}
