package com.example.urbanarc.Shopkeeper.POJO;

public class POJOAllProduct {
    String id,image,category,name,price,offer,discription,rating,delivery;

    public POJOAllProduct(String id, String image, String category, String name, String price, String offer, String discription, String rating, String delivery) {
        this.id = id;
        this.image = image;
        this.category = category;
        this.name = name;
        this.price = price;
        this.offer = offer;
        this.discription = discription;
        this.rating = rating;
        this.delivery = delivery;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
