package manish.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import manish.data.TwitterProfile;
import manish.data.User;
import manish.util.DataBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mmaheshwari on 27/11/18.
 */
public class UserService {

    @GraphQLQuery(name = "user", description = "Returns user for this id")
    public User getById(@GraphQLArgument(name = "id") Integer id) {
        return DataBuilder.users.stream().
                filter(user -> user.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    @GraphQLQuery(name = "twitter", description = "Returns twitter profile for this user")
    public List<TwitterProfile> getTwitterProfile(@GraphQLContext User user) {
        return DataBuilder.twitterProfiles.stream()
                .filter(twitterProfile -> twitterProfile.getHandle().contains(user.getName()))
                .collect(Collectors.toList());
    }


    @GraphQLQuery(name = "users", description = "Returns list of all users")
    public List<User> getById() {
        return DataBuilder.users;
    }

    /*
    1. Client will use epoch time for Date scaler.
    2. Note the enum scalar
     */
    @GraphQLMutation(name = "adduser", description = "Add new user")
    public User createUser(@GraphQLArgument(name = "name") String name,
                           @GraphQLArgument(name = "id") Integer id, @GraphQLArgument(name = "regDate") Date regDate,
                           @GraphQLArgument(name = "gender") User.GENDER gender) {
        User user = new User(id, name, regDate, gender);
        DataBuilder.users.add(user);
        return user;
    }


}
