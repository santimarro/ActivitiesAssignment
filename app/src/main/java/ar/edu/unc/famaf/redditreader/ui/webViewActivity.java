package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import ar.edu.unc.famaf.redditreader.R;

public class webViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        setContentView(webview);

        Intent intent = this.getIntent();
        String url = intent.getStringExtra(NewsDetailActivityFragment.URL);
        webview.loadUrl(url);

    }
}
