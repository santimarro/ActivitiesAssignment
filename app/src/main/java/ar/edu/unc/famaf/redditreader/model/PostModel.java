package ar.edu.unc.famaf.redditreader.model;



/**
 * Created by smarro on 9/29/16.
 */
public class PostModel {

    private String mTitle;
    private String mContent;
    private String mSubreddit;
    private int mComments;
    private String mPostDate;

    public PostModel(String mTitle, String mContent, String mSubreddit, int mComments, String mPostDate, String mImage) {
        this.mTitle = mTitle;
        this.mContent = mContent;
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

    private String mImage;

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
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

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}

