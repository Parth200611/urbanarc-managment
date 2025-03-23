package com.example.urbanarc.Delivery.POJO;

public class POJOHis {
    String image,productname,price,username,address,payment,deluser;

    public POJOHis(String image, String productname, String price, String username, String address, String payment, String deluser) {
        this.image = image;
        this.productname = productname;
        this.price = price;
        this.username = username;
        this.address = address;
        this.payment = payment;
        this.deluser = deluser;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDeluser() {
        return deluser;
    }

    public void setDeluser(String deluser) {
        this.deluser = deluser;
    }
}
