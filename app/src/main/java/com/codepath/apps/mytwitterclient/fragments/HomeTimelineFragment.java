package com.codepath.apps.mytwitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepath.apps.mytwitterclient.TwitterApplication;
import com.codepath.apps.mytwitterclient.TwitterClient;
import com.codepath.apps.mytwitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.mytwitterclient.models.Tweet;
import com.codepath.apps.mytwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by kshantaraman on 12/13/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    @Override
    protected void initialLoadWithInternet(JsonHttpResponseHandler handler) {
        // we have internet, clear out old tweets
        Tweet.deleteAllBy(User.getCurrentUser());
        client.getNewTimelineEntries(handler);
    }

    @Override
    protected void initialLoadNoInternet(TweetsArrayAdapter aTweets) {
        aTweets.addAll(Tweet.getAllBy(User.getCurrentUser()));
        aTweets.notifyDataSetChanged();
    }

    @Override
    protected void onSwipeUp(JsonHttpResponseHandler handler) {
        client.getNewTimelineEntries(handler, getNewest_id());
    }

    @Override
    protected void onSwipeDown(JsonHttpResponseHandler handler) {
        client.getOlderTimelineEntries(handler, getOldest_id());
    }
}
