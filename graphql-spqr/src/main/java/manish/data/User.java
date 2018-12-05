package manish.data;

import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.Date;

/**
 * Created by mmaheshwari on 27/11/18.
 */
public class User {

    private String name;
    private Integer id;
    private Date registrationDate;

    private GENDER gender;


    public enum GENDER {M,F;};
    public User(Integer id, String name, Date registrationDate, GENDER gender) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.gender = gender;
    }


    @GraphQLQuery(name = "id", description = "The id of user")
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    //@GraphQLQuery(name="name", description = "User's name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @GraphQLQuery(name = "regDate", description = "Registration date of user")
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @GraphQLQuery(name = "gender", description = "Gender of user")
    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }


}
