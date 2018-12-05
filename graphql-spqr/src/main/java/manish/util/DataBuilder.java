package manish.util;

import manish.data.TwitterProfile;
import manish.data.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mmaheshwari on 27/11/18.
 */
public class DataBuilder {

    public static List<User> users = new LinkedList<>();
    public static List<TwitterProfile> twitterProfiles = new LinkedList<>();

    public static void build() {
        users.add(new User(101, "manish", new Date(1538382021000L), User.GENDER.M));
        users.add(new User(102, "amit", new Date(1506846021000L), User.GENDER.M));
        users.add(new User(103, "tarun", new Date(1475310021000L), User.GENDER.M));
        users.add(new User(104, "shahid", new Date(1443687621000L), User.GENDER.M));
        users.add(new User(105, "hemant", new Date(1412151621000L), User.GENDER.M));
        users.add(new User(106, "nonie", new Date(1412151621000L), User.GENDER.F));


        twitterProfiles.add(new TwitterProfile("manish.twitter", 1000));
        twitterProfiles.add(new TwitterProfile("amit.twitter", 2000));
        twitterProfiles.add(new TwitterProfile("tarun.twitter", 3000));
        twitterProfiles.add(new TwitterProfile("shahid.twitter", 4000));
        twitterProfiles.add(new TwitterProfile("hemant.twitter", 5000));
        twitterProfiles.add(new TwitterProfile("nonie.twitter", 7000));

    }
}
