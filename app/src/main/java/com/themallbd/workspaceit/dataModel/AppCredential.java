package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by rajib on 2/11/16.
 */
public class AppCredential {

    public int id;
    public String email;
    public User user;
    public String accesstoken;


    public AppCredential() {
        this.id = 0;
        this.email = "";
        this.accesstoken = "";
        this.user = new User();

    }
}
