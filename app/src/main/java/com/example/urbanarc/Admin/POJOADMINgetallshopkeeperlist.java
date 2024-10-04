package com.example.urbanarc.Admin;

public class POJOADMINgetallshopkeeperlist {
    String id,image,name,emailid,mobileno,addrss,username,password;

    public POJOADMINgetallshopkeeperlist(String id, String image, String name, String emailid, String mobileno, String addrss, String username, String password) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.emailid = emailid;
        this.mobileno = mobileno;
        this.addrss = addrss;
        this.username = username;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getAddrss() {
        return addrss;
    }

    public void setAddrss(String addrss) {
        this.addrss = addrss;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
