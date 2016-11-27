package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsActivity extends AppCompatActivity {
    String POST_DETAIL = "post_detail";
    static final int LOGGIN_KEY = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_in) {
            NewsActivityFragment newsfragment = (NewsActivityFragment)
                    getSupportFragmentManager().findFragmentById(R.id.news_activity_fragment_id);
            Intent intentLogin = new Intent(NewsActivity.this, LoginActivity.class);
            startActivityForResult(intentLogin, LOGGIN_KEY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void onPostItemPicked(PostModel post) {
        // Guardo el post seleccionado en el intent y arranco la activity
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra(POST_DETAIL, post);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == LOGGIN_KEY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String user = data.getStringExtra("email");
                    Resources res = getResources();
                    //String log_message = String.format(res.getString(R.string.login_message), user);


                    //TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
                    //textView.setText(log_message);
                    // The user picked a contact.
                    // The Intent's data Uri identifies which contact was selected.

                    // Do something with the contact here (bigger example below)
                }
            }
        }
    }
}