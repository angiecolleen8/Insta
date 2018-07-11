package com.codepath.acfoley.insta;

import android.app.Application;

import com.parse.Parse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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

/*
        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options. */
       /* OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);*/

        /*//test creation of object
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        */
    }
}
