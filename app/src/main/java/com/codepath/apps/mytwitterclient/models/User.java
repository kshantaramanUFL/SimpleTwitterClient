package com.codepath.apps.mytwitterclient.models;

/**
 * Created by kshantaraman on 12/13/15.
 */
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.codepath.apps.mytwitterclient.TwitterApplication;
import com.codepath.apps.mytwitterclient.lib.PreferenceManager;
import com.codepath.apps.mytwitterclient.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by devin on 11/3/15.
 */
@Table(name = "Users")
public class User extends Model implements Serializable, Parcelable {

    @Column(name = "Name")
    private String name;

    @Column(name = "Uid")
    private Long uid;

    @Column(name = "ScreenName", index = true)
    private String screenName;

    @Column(name = "ProfileImageUrl")
    private String profileImageUrl;

    @Column(name = "ProfileImageUrlHttps")
    private String profileImageUrlHttps;

    @Column(name = "ProfileBannerUrl")
    private String profileBannerUrl;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "FollowersCount")
    private Long followersCount;

    @Column(name = "FollowingCount")
    private Long followingCount;

    @Column(name = "StatusesCount")
    private Long statusesCount;

    public User() { super(); }

    // This method does not affect the foreign key creation.
    public List<Tweet> tweets() {
        return getMany(Tweet.class, "User");
    }

    public static void deleteAll() {
        new Delete().from(User.class).execute();
    }

    // by default we should save to the db
    public static User fromJSON(JSONObject obj) {
        return fromJSON(obj, true);
    }

    // Returns a User given the expected JSON
    public static User fromJSON(JSONObject obj, boolean saveToDb) {
        try {
            User u = new User();
            u.name = obj.getString("name");
            u.uid = obj.getLong("id");
            u.screenName = obj.getString("screen_name");
            u.profileImageUrl = obj.getString("profile_image_url");
            u.profileImageUrlHttps = obj.getString("profile_image_url_https");
            if (obj.has("profile_banner_url")) {
                u.profileBannerUrl = obj.getString("profile_banner_url");
            }
            u.followersCount = obj.getLong("followers_count");
            u.followingCount = obj.getLong("friends_count");
            u.statusesCount = obj.getLong("statuses_count");
            u.createdAt = new Date(System.currentTimeMillis());
            if (saveToDb) {
                u.save();
            }
            return u;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // get a user by screen name. if there are more than one (shouldn't be),
    // return the first. If there are zero found, or screenName is null, return null.
    public static User fromDbByScreenName(String screenName) {
        if (screenName == null) {
            return null;
        }

        List<User> users = new Select()
                .from(User.class)
                .where("ScreenName = ?", screenName)
                .orderBy("Name ASC")
                .execute();

        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }


    // save the logged-in user object to the user preferences
    public static void saveCurrentUserToPrefs(final Context context) {
        TwitterClient client = TwitterApplication.getRestClient();

        // get info about the logged-in user
        client.getLoggedInUser(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                TwitterClient client = TwitterApplication.getRestClient();
                User user = User.fromJSON(json, false);
                String screenName = user.getScreenName();

                // use that info to query the api for the full user details, then save them.
                client.getUser(screenName, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                        User user = User.fromJSON(json);
                        // clear old data
                        PreferenceManager prefs = PreferenceManager.getInstance();
                        prefs.remove("logged_in_screen_name");
                        // store new data
                        prefs.set("logged_in_screen_name", user.getScreenName());
                    }
                });
            }
        });
    }

    public static void removeCurrentUserFromPrefs(final Context context) {
        PreferenceManager prefs = PreferenceManager.getInstance();
        prefs.remove("logged_in_screen_name");
    }

    public static User getCurrentUser() {
        PreferenceManager prefs = PreferenceManager.getInstance();
        String screenName = prefs.getString("logged_in_screen_name");
        return User.fromDbByScreenName(screenName);
    }

    public String toString() {
        return getScreenNameWithAmpersand() + "; " + getName() + "; " + getUid();
    }

    // getters

    public Long getFollowersCount() { return followersCount; }
    public Long getStatusesCount() { return statusesCount; }
    public Long getFollowingCount() {
        return followingCount;
    }
    public Long getUid() {
        return uid;
    }
    public String getScreenName() {
        return screenName;
    }
    public String getScreenNameWithAmpersand() { return "@" + screenName; }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }
    public String getName() { return name; }
    public String getProfileBannerUrl() { return profileBannerUrl; }


    // parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.uid);
        dest.writeString(this.screenName);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.profileImageUrlHttps);
        dest.writeString(this.profileBannerUrl);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeValue(this.followersCount);
        dest.writeValue(this.followingCount);
        dest.writeValue(this.statusesCount);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.uid = (Long) in.readValue(Long.class.getClassLoader());
        this.screenName = in.readString();
        this.profileImageUrl = in.readString();
        this.profileImageUrlHttps = in.readString();
        this.profileBannerUrl = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        this.followersCount = (Long) in.readValue(Long.class.getClassLoader());
        this.followingCount = (Long) in.readValue(Long.class.getClassLoader());
        this.statusesCount = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
