package com.example.urbanarc.User.POJOCLASS;

public class POJOUserGetAddtoCartdata {

    String id,username,shopname,image,category,productname,price,offer,discription,rating,deliveryday,productid;

    public POJOUserGetAddtoCartdata(String id, String username, String shopname, String image, String category, String productname, String price, String offer, String discription, String rating, String deliveryday, String productid) {
        this.id = id;
        this.username = username;
        this.shopname = shopname;
        this.image = image;
        this.category = category;
        this.productname = productname;
        this.price = price;
        this.offer = offer;
        this.discription = discription;
        this.rating = rating;
        this.deliveryday = deliveryday;
        this.productid = productid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDeliveryday() {
        return deliveryday;
    }

    public void setDeliveryday(String deliveryday) {
        this.deliveryday = deliveryday;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
