package com.codepath.acfoley.insta.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    //encapsulate post object
        //String var names correspond to the column names on ParseDashboard
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE= "image";
    private static final String KEY_USER = "user";

    public String getDescription() {
        return getString(KEY_DESCRIPTION); //getString method for ParseObject requires column name ("key") for ParseDashboard
    }

    //used for posting - return types  correspond to what ParseDashboard can accept
    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description); //key value pair: put String description in our description column, found by KEY_DESCRIPTION
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) { //TODO - double check this param. I originally had it as String user, but ParseUser makes more sense I think
        put(KEY_USER, user);
    }

    public static class Query extends ParseQuery<Post> {
        public Query() {
            super(Post.class);
        }

        public Query getTop() { //get top posts
            setLimit(100); //gets top 20 posts
            return this;
        }

        public Query withUser() {
            include("user"); //this is how we queried a post with a user on Parse Dashboard
            return this;
        }
    }
}
