package com.example.urbanarc.Admin.POJOCLASS;

public class POJOADminGetALLDELIVERYLIst {
    String id,name,image,username,rating,emailid,mobileno,orders,password;

    public POJOADminGetALLDELIVERYLIst(String id, String name, String image, String username, String rating, String emailid, String mobileno, String orders, String password) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.username = username;
        this.rating = rating;
        this.emailid = emailid;
        this.mobileno = mobileno;
        this.orders = orders;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
