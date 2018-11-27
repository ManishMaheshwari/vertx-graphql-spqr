package manish.data;

import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * Created by mmaheshwari on 27/11/18.
 */
public class TwitterProfile {

    private String handle;
    private Integer numTweets;

    @GraphQLQuery(name = "handle", description = "Twitter handle")
    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    @GraphQLQuery(name = "numTweets", description = "Number of Tweets")
    public Integer getNumTweets() {
        return numTweets;
    }

    public void setNumTweets(Integer numTweets) {
        this.numTweets = numTweets;
    }

    public TwitterProfile(String handle, Integer numTweets) {
        this.handle = handle;
        this.numTweets = numTweets;
    }


}
