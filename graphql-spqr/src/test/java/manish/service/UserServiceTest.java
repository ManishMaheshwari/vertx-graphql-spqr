package manish.service;

import manish.data.TwitterProfile;
import manish.data.User;
import manish.util.DataBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mmaheshwari on 27/11/18.
 */
public class UserServiceTest {
    @Before
    public void setUp() throws Exception {
        DataBuilder.build();
    }

    @Test
    public void getById() throws Exception {
        UserService userService = new UserService();
        User user = userService.getById(102);
        assertTrue(user.getName().equals("amit"));
    }

    @Test
    public void getTwitterProfile() throws Exception {
        UserService userService = new UserService();
        List<TwitterProfile> profiles = userService.getTwitterProfile(new User(102, "amit", null));
        assertEquals(1, profiles.size());
        TwitterProfile profile = profiles.get(0);
        assertTrue(profile.getHandle().equals("amit.twitter"));
        assertEquals(2000, profile.getNumTweets().intValue());
    }

}