package com.example.urbanarc.User.POJOCLASS;

public class POJOMYORDERS {
    String image,name,productid;

    public POJOMYORDERS(String image, String name,String productid) {
        this.image = image;
        this.name = name;
        this.productid = productid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
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
}
