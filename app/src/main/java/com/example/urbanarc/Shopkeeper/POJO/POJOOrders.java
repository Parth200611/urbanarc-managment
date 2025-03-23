package com.example.urbanarc.Shopkeeper.POJO;

public class POJOOrders {
     String id, username, name, mobileno, latitude, longitude, address, image, productname, price, offer, description, deliveryday, shopname, category, productid, payment;

    public POJOOrders(String id, String username, String name, String mobileno, String latitude, String longitude, String address, String image, String productname, String price, String offer, String description, String deliveryday, String shopname, String category, String productid, String payment) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.mobileno = mobileno;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.image = image;
        this.productname = productname;
        this.price = price;
        this.offer = offer;
        this.description = description;
        this.deliveryday = deliveryday;
        this.shopname = shopname;
        this.category = category;
        this.productid = productid;
        this.payment = payment;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryday() {
        return deliveryday;
    }

    public void setDeliveryday(String deliveryday) {
        this.deliveryday = deliveryday;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
