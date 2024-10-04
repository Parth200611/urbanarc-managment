package com.example.urbanarc.User;

public class POJOgetallcategory {
    //pojo plane old java object
    //reusablity
    //get and set multiple data
    String Id,CategoryImage,CategoryName;

    public POJOgetallcategory(String id, String categoryImage, String categoryName) {
        Id = id;
        CategoryImage = categoryImage;
        CategoryName = categoryName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        CategoryImage = categoryImage;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
