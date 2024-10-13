package com.example.urbanarc.Admin.POJOCLASS;

public class POJOADMINGetallUserData {
    String id,images,name,emailid,mobileno,username,userpassword;

    public POJOADMINGetallUserData(String id, String images, String name, String emailid, String mobileno, String username, String userpassword) {
        this.id = id;
        this.images = images;
        this.name = name;
        this.emailid = emailid;
        this.mobileno = mobileno;
        this.username = username;
        this.userpassword = userpassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
