package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by smarro on 10/27/16.
 */

public class Parser {


    public Parser() {

    }

    public Listing readJsonStream(InputStream in) throws IOException {
        List<PostModel> list = new ArrayList<>();
        String after = null;
        String before = null;

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("data")) {
                    reader.beginObject();
                } else if (name.equals("children")) {
                    list = readPostModelArray(reader);
                    reader.endObject();
                } else if (name.equals("after")) {
                    after = reader.nextString();
                } else if (name.equals("before")) {
                    before = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }

        } finally {
            reader.close();
        }

        Listing mList = new Listing(list, after, before);
        return mList;
    }

    public PostModel readPostModel(JsonReader reader) throws IOException {
        String subreddit = null;
        String title = null;
        String author = null;
        String thumbnail = null;
        int num_comments = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("data")) {
                reader.beginObject();
            } else if (name.equals("subreddit")) {
                subreddit = reader.nextString();
            } else if (name.equals("author")) {
                author = reader.nextString();
            } else if (name.equals("thumbnail")) {
                thumbnail = reader.nextString();
            } else if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("num_comments")) {
                num_comments = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new PostModel(title, author, subreddit, num_comments,null ,thumbnail);
    }

    public List<PostModel> readPostModelArray(JsonReader reader) throws IOException {
        List<PostModel> posts = new ArrayList<PostModel>();

        reader.beginArray();
        while (reader.hasNext()) {
            posts.add(readPostModel(reader));
        }
        reader.endArray();
        return posts;
    }

}
