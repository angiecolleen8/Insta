package com.codepath.acfoley.insta;

import android.app.Application;

import com.codepath.acfoley.insta.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class); //Parse needs this line to know that we are creating a custom Parse object.
            // Tells Parse that this Post model is a custom Parse model that we created to encapsulate our data when dealing with Parse classes


        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        //parse configuration needed for setting up Parse server
        // set applicationId, and server server based on the values in the Heroku settings.

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.APP_ID))
                .clientKey(getString(R.string.MASTER_KEY)) //APP_ID AND MASTER_KEY in secrets
                .server("http://angela-colleens-insta.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);

        /*//test creation of object
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        */
    }
}
