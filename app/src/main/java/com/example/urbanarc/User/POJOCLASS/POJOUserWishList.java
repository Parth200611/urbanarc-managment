package com.example.urbanarc.User.POJOCLASS;

public class POJOUserWishList {
    String id,username,shopname,image,category,paroductname,price,offer,discription,delivery,productid,rating;

    public POJOUserWishList(String id, String username, String shopname, String image, String category, String paroductname, String price, String offer, String discription, String delivery, String productid,String rating) {
        this.id = id;
        this.username = username;
        this.shopname = shopname;
        this.image = image;
        this.category = category;
        this.paroductname = paroductname;
        this.price = price;
        this.offer = offer;
        this.discription = discription;
        this.delivery = delivery;
        this.productid = productid;
        this.rating =rating;
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

    public String getParoductname() {
        return paroductname;
    }

    public void setParoductname(String paroductname) {
        this.paroductname = paroductname;
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

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
