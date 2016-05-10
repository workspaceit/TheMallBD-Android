package com.themallbd.workspaceit.dataModel;

/**
 * Created by rajib on 2/15/16.
 */
public class User {

    public int id;
    public String firstName;
    public String lastName;
    public String createdOn;
    public String email;
    public Role role;
    public String status;
    public String phone;
    public String image;
    public UserDetails userDetails;

    public User() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.createdOn = "";
        this.email = "";
        this.role = new Role();
        this.status = "";
        this.phone = "";
        this.image = "";
        this.userDetails = new UserDetails();
    }
}
