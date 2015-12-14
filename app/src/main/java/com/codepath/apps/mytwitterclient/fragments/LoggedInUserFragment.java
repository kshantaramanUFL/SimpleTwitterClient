package com.codepath.apps.mytwitterclient.fragments;

import android.os.Bundle;

import com.codepath.apps.mytwitterclient.TwitterApplication;
import com.codepath.apps.mytwitterclient.TwitterClient;

/**
 * Created by kshantaraman on 12/13/15.
 */
public class LoggedInUserFragment extends UserDetailsFragment {

    protected TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }
}
