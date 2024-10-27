package com.example.urbanarc.User.POJOCLASS;

public class POJOgetCateggroryWisePoduct {
    String id,image,category,productname,price,offer,discription,rating,delivery;

    public POJOgetCateggroryWisePoduct(String id, String image, String category, String productname, String price, String offer, String discription, String rating,String delivery) {
        this.id = id;
        this.image = image;
        this.category = category;
        this.productname = productname;
        this.price = price;
        this.offer = offer;
        this.discription = discription;
        this.rating = rating;
        this.delivery=delivery;
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

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
