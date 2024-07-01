package com.udacity.jwdnd.course1.cloudstorage.model;


//credentialid INT PRIMARY KEY auto_increment,
//url VARCHAR(100),
//username VARCHAR (30),
//key_ VARCHAR,
//password VARCHAR,
//userid INT,
//foreign key (userid) references USERS(userid)

public class Credential {

    private Integer credentialid;
    private String url;

    private String username;

    private String key_;

    private String password;

    private Integer userid;

    private String originalpassword;

    public Credential() {
    }

    public Credential(Integer credentialid, String url, String username, String key_, String password, Integer userid) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.key_ = key_;
        this.password = password;
        this.userid = userid;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey_() {
        return key_;
    }

    public void setKey_(String key_) {
        this.key_ = key_;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getOriginalpassword() {
        return originalpassword;
    }

    public void setOriginalpassword(String originalpassword) {
        this.originalpassword = originalpassword;
    }
}

