# Twitter Client

is an android app that allows a user to view their Twitter timeline and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **30** hours spent in total.

## User Stories


The following functionality is completed:

* 
* [x] User can **switch between Timeline and Mention views using tabs**
  * [x] User can view their home timeline tweets.
  * [x] User can view the recent mentions of their username.
  * [x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [x] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [x] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [x] Profile view includes that user's timeline
* [x] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews) any of these timelines (home, mentions, user) by scrolling to the bottom
* [x] Links are clickable and open in browser

The following **additional** features are implemented:

* [x] Added the ability to log out
* [x] Images use slightly rounded corners

## Video Walkthroughs

Here is a walkthrough of implemented user stories:

<img src='https://github.com/kshantaramanUFL/SimpleTwitterClient/raw/master/app/Twitter_Client.gif' alt='Video Walkthrough'/>

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Moving the TimelineActivity into a Fragment was not too difficult. The more difficult and time-consuming aspect of this week's homework was modeling and implementing the shared user details fragment and ensuring that everything got hooked in correctly.  It was also challenging to figure out the best logic when the internet is not available.  The app will pull items out of the database if the internet is down.  It also caches the User objects to reduce API calls.  I am also pretty proud of the abstract class TweetsListFragment and the two fragments that implement it -- HomeTimelineFragment and MentionsTimelineFragment.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- com.astuetz:pagerslidingtabstrip - Sliding Tab View in Home Timeline
