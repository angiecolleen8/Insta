package com.codepath.acfoley.insta.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
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

    public void putImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(String user) {
        put(KEY_USER, user);
    }
}
