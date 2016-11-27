package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsDetailActivity extends AppCompatActivity {
    String POST_DETAIL = "post_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent intent = this.getIntent();

        Bundle args = new Bundle();
        PostModel post = (PostModel) intent.getSerializableExtra(POST_DETAIL);
        args.putSerializable("POST_DETAIL", post);

        // Launchear el fragment.
    }
}
