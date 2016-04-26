package com.workspaceit.themallbd.dataModel;

/**
 * Created by Mausum on 4/26/2016.
 */
public class Review {
    public int id;
    public String note;
    public float rating;
    public String createdOn;
    public String createdBy;
    public String updatedOn;
    public String updatedBy;
    public User customer;



    public Review(){
        this.id=0;
        this.note="";
        this.rating=0;
        this.createdOn="";
        this.createdBy="";
        this.updatedOn="";
        this.updatedBy="";
        this.customer=new User();



    }
}

