package manish.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import manish.data.TwitterProfile;
import manish.data.User;
import manish.util.DataBuilder;

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

}
