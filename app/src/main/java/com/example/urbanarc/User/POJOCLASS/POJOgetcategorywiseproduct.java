package com.example.urbanarc.User.POJOCLASS;



public class POJOgetcategorywiseproduct {
    String id,categoryname,productimage,categoryproductname,categoryShopname,
            categoryproductprice,productdiscription,productoffer,productrating;

    public POJOgetcategorywiseproduct(String id, String categoryname, String productimage, String categoryproductname, String categoryShopname,
                                      String categoryproductprice, String productdiscription, String productoffer, String productrating) {

        this.id = id;
        this.categoryname = categoryname;
        this.productimage = productimage;
        this.categoryproductname = categoryproductname;
        this.categoryShopname = categoryShopname;
        this.categoryproductprice = categoryproductprice;
        this.productdiscription = productdiscription;
        this.productoffer = productoffer;
        this.productrating = productrating;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getCategoryproductname() {
        return categoryproductname;
    }

    public void setCategoryproductname(String categoryproductname) {
        this.categoryproductname = categoryproductname;
    }

    public String getCategoryShopname() {
        return categoryShopname;
    }

    public void setCategoryShopname(String categoryShopname) {
        this.categoryShopname = categoryShopname;
    }

    public String getCategoryproductprice() {
        return categoryproductprice;
    }

    public void setCategoryproductprice(String categoryproductprice) {
        this.categoryproductprice = categoryproductprice;
    }

    public String getProductdiscription() {
        return productdiscription;
    }

    public void setProductdiscription(String productdiscription) {
        this.productdiscription = productdiscription;
    }

    public String getProductoffer() {
        return productoffer;
    }

    public void setProductoffer(String productoffer) {
        this.productoffer = productoffer;
    }

    public String getProductrating() {
        return productrating;
    }

    public void setProductrating(String productrating) {
        this.productrating = productrating;
    }
}
