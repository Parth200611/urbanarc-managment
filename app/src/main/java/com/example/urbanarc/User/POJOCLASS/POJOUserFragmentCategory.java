package com.example.urbanarc.User.POJOCLASS;

public class POJOUserFragmentCategory {
    String id,categoryimage,categoryname;

    public POJOUserFragmentCategory(String id, String categoryimage, String categoryname) {
        this.id = id;
        this.categoryimage = categoryimage;
        this.categoryname = categoryname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryimage() {
        return categoryimage;
    }

    public void setCategoryimage(String categoryimage) {
        this.categoryimage = categoryimage;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
