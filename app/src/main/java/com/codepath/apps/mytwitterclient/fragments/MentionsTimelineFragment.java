package com.codepath.apps.mytwitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mytwitterclient.TwitterApplication;
import com.codepath.apps.mytwitterclient.TwitterClient;
import com.codepath.apps.mytwitterclient.adapters.TweetsArrayAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by kshantaraman on 12/13/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    @Override
    protected void initialLoadWithInternet(JsonHttpResponseHandler handler) {
        Log.d("mentions frag", "initial load with internet");
        client.getNewMentionsEntries(handler);
    }

    @Override
    protected void initialLoadNoInternet(TweetsArrayAdapter aTweets) {
    }

    @Override
    protected void onSwipeUp(JsonHttpResponseHandler handler) {
        Log.d("mentions frag", "onswipeup");
        client.getNewMentionEntries(handler, getNewest_id());
    }

    @Override
    protected void onSwipeDown(JsonHttpResponseHandler handler) {
        Log.d("mentions frag", "onswipedown");
        client.getOlderMentionsEntries(handler, getOldest_id());
    }
}
