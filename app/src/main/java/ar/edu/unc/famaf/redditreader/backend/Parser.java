package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static android.R.attr.name;
import static android.R.id.list;

/**
 * Created by smarro on 10/27/16.
 */

public class Parser {


    public Parser() {

    }

    public Listing readJsonStream(InputStream in) throws IOException {
        Listing mList = null;
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("data")) {
                    mList = readData(reader);
                } else {
                    reader.skipValue();
                }
            }

        } finally {
            reader.close();
        }

        return mList;
    }


    public Listing readData(JsonReader reader) throws  IOException {
        List<PostModel> list = new ArrayList<>();
        String after = null;
        String before = null;
        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "children":
                    list = readPostModelArray(reader);
                    break;
                case "after":
                    after = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Listing(list, after, before);
    }


    public List<PostModel> readPostModelArray(JsonReader reader) throws IOException {
        List<PostModel> posts = new ArrayList<PostModel>();

        reader.beginArray();
        while (reader.hasNext()) {
            posts.add(readDataPostModel(reader));
        }
        reader.endArray();
        return posts;
    }

    public PostModel readDataPostModel(JsonReader reader) throws  IOException {
        PostModel post = null;
        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("data")) {
                post = readPostModel(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return post;
    }

    public PostModel readPostModel(JsonReader reader) throws IOException {
        String subreddit = null;
        String title = null;
        String author = null;
        String thumbnail = null;
        String url = null;
        int num_comments = 0;
        long epoch = 0;
        String id = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "subreddit":
                    subreddit = reader.nextString();
                    break;
                case "author":
                    author = reader.nextString();
                    break;
                case "thumbnail":
                    thumbnail = reader.nextString();
                    break;
                case "title":
                    title = reader.nextString();
                    break;
                case "num_comments":
                    num_comments = reader.nextInt();
                    break;
                case "created":
                    epoch = reader.nextLong() * 1000;
                    break;
                case "id":
                    id = reader.nextString();
                    break;
                case "url":
                    url = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();


        Date date = new Date(epoch);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = formatter.format(date);

        formatter = new SimpleDateFormat("hh:mm a");
        String PostDate = formatter.format(date);

        return new PostModel(id, title, author, subreddit, num_comments, PostDate ,thumbnail, url);
    }

}
