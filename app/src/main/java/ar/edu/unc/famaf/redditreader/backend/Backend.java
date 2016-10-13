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

        PostModel p1 = new PostModel("This is not concrete..this is New Delhi","ophionn","r/todayilearned", 1000, "4 hs", "https://b.thumbs.redditmedia.com/xj1jR4ke30GHaSNtP-zs8HL7eWh46hTTBzdohs2CnkU.jpg");
        mListPostModel.add(p1);

        PostModel p2 = new PostModel("It is true","kenny","r/todayilearned",56, "1 hs", "https://b.thumbs.redditmedia.com/Tp9CqdhxKiMiNXmhqc_QFTWGw1GipKcCc9_PmZ3z5-M.jpg");
        mListPostModel.add(p2);

        PostModel p3 = new PostModel("My mom cut into her carrot and found a smiley face.","mark","r/radiohead", 23, "1 hs", "https://b.thumbs.redditmedia.com/za3XzCLzP7oB8a53lGJM2ziznNe5NA3IidRoKnz2Wws.jpg");
        mListPostModel.add(p3);

        PostModel p4 = new PostModel("First smile ever","john","r/funny", 23, "1 hs", "https://a.thumbs.redditmedia.com/KP-qNPsmpmJYRkRUUB3ZzvpHJFtiQum3EHav9-6TWz8.jpg");
        mListPostModel.add(p4);

        PostModel p5 = new PostModel("The neighbors dog loves me","turk","r/aww", 23, "1 hs", "https://b.thumbs.redditmedia.com/gc8XIvLJaqBnuI4VvQZ2kir89DpBVri45eAn_HZ_raY.jpg");
        mListPostModel.add(p5);
        PostModel p6 = new PostModel("This is not concrete..this is New Delhi","ophionn","r/todayilearned", 1000, "4 hs", "https://b.thumbs.redditmedia.com/xj1jR4ke30GHaSNtP-zs8HL7eWh46hTTBzdohs2CnkU.jpg");
        mListPostModel.add(p6);

        PostModel p7 = new PostModel("It is true","kenny","r/todayilearned",56, "1 hs", "https://b.thumbs.redditmedia.com/Tp9CqdhxKiMiNXmhqc_QFTWGw1GipKcCc9_PmZ3z5-M.jpg");
        mListPostModel.add(p7);

        PostModel p8 = new PostModel("My mom cut into her carrot and found a smiley face.","mark","r/radiohead", 23, "1 hs", "https://b.thumbs.redditmedia.com/za3XzCLzP7oB8a53lGJM2ziznNe5NA3IidRoKnz2Wws.jpg");
        mListPostModel.add(p8);

        PostModel p9 = new PostModel("First smile ever","john","r/funny", 23, "1 hs", "https://a.thumbs.redditmedia.com/KP-qNPsmpmJYRkRUUB3ZzvpHJFtiQum3EHav9-6TWz8.jpg");
        mListPostModel.add(p9);

        PostModel p10 = new PostModel("The neighbors dog loves me","turk","r/aww", 23, "1 hs", "https://b.thumbs.redditmedia.com/gc8XIvLJaqBnuI4VvQZ2kir89DpBVri45eAn_HZ_raY.jpg");
        mListPostModel.add(p10);
        PostModel p11 = new PostModel("This is not concrete..this is New Delhi","ophionn","r/todayilearned", 1000, "4 hs", "https://b.thumbs.redditmedia.com/xj1jR4ke30GHaSNtP-zs8HL7eWh46hTTBzdohs2CnkU.jpg");
        mListPostModel.add(p11);

        PostModel p12 = new PostModel("It is true","kenny","r/todayilearned",56, "1 hs", "https://b.thumbs.redditmedia.com/Tp9CqdhxKiMiNXmhqc_QFTWGw1GipKcCc9_PmZ3z5-M.jpg");
        mListPostModel.add(p12);

        PostModel p13 = new PostModel("My mom cut into her carrot and found a smiley face.","mark","r/radiohead", 23, "1 hs", "https://b.thumbs.redditmedia.com/za3XzCLzP7oB8a53lGJM2ziznNe5NA3IidRoKnz2Wws.jpg");
        mListPostModel.add(p13);

        PostModel p14 = new PostModel("First smile ever","john","r/funny", 23, "1 hs", "https://a.thumbs.redditmedia.com/KP-qNPsmpmJYRkRUUB3ZzvpHJFtiQum3EHav9-6TWz8.jpg");
        mListPostModel.add(p14);

        PostModel p15 = new PostModel("The neighbors dog loves me","turk","r/aww", 23, "1 hs", "https://b.thumbs.redditmedia.com/gc8XIvLJaqBnuI4VvQZ2kir89DpBVri45eAn_HZ_raY.jpg");
        mListPostModel.add(p15);
    }

    public List<PostModel> getTopPosts() {
        return mListPostModel;
    }

}
