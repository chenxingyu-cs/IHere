package cmu.sv.flubber.ihere.entities;

import java.util.ArrayList;

/**
 * Created by xingyuchen on 4/12/16.
 */
public class User {
    private int userId;
    private String userName;
    private String email;
    private String description;
    private int gender;
    private ArrayList<ITag> tags;

    public User() {
        super();
    }

    public User(String userName, String email, String description, int gender, ArrayList<ITag> tags) {
        this.userName = userName;
        this.email = email;
        this.description = description;
        this.gender = gender;
        this.tags = tags;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public ArrayList<ITag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<ITag> tags) {
        this.tags = tags;
    }
}
