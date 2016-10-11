package ar.edu.unc.famaf.redditreader.model;


import static android.R.attr.thumbnail;

/**
 * Created by smarro on 9/29/16.
 */
public class PostModel {

    private String mTitle;
    private String mAuthor;
    private String mSubreddit;
    private int mComments;
    private String mPostDate;
    private int mImage;


    public PostModel(String mTitle, String mAuthor, String mSubreddit, int mComments, String mPostDate, int mImage) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mSubreddit = mSubreddit;
        this.mComments = mComments;
        this.mPostDate = mPostDate;
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmPostDate() {
        return mPostDate;
    }

    public void setmPostDate(String mPostDate) {
        this.mPostDate = mPostDate;
    }

    public int getmComments() {
        return mComments;
    }

    public void setmComments(int mComments) {
        this.mComments = mComments;
    }

    public String getmSubreddit() {
        return mSubreddit;
    }

    public void setmSubreddit(String mSubreddit) {
        this.mSubreddit = mSubreddit;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }
}

