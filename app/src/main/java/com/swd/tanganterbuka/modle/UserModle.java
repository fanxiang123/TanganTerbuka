package com.swd.tanganterbuka.modle;

public class UserModle {
    //注册后系统生成的userid，保留本地
    private String user_id;
    //用户注册时的手机号
    private String user_mobile;
    //昵称
    private String user_name;
    //用户logo
    private String user_logo;

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }
    public String getUser_id(){
        return this.user_id;
    }
    public void setUser_mobile(String user_mobile){
        this.user_mobile = user_mobile;
    }
    public String getUser_mobile(){
        return this.user_mobile;
    }
    public void setUser_name(String user_name){
        this.user_name = user_name;
    }
    public String getUser_name(){
        return this.user_name;
    }
    public void setUser_logo(String user_logo){
        this.user_logo = user_logo;
    }
    public String getUser_logo(){
        return this.user_logo;
    }
}
