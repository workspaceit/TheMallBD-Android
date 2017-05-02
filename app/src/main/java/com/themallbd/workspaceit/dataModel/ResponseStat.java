package com.themallbd.workspaceit.dataModel;

/**
 * Created by rajib on 2/11/16.
 */
public class ResponseStat {

    public String msg;
    public boolean status;
    public boolean isLogin;
    public ResponseStat() {
        this.msg = "";
        this.status = false;
        this.isLogin = true;
    }
}
