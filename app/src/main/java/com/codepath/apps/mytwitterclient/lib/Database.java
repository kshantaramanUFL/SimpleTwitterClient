package com.codepath.apps.mytwitterclient.lib;

import com.codepath.apps.mytwitterclient.models.Tweet;
import com.codepath.apps.mytwitterclient.models.User;

/**
 * Created by kshantaraman on 12/13/15.
 */
public class Database {
    public static void reset() {
        Tweet.deleteAll();
        User.deleteAll();
    }
}
