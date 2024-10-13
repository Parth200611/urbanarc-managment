package com.example.urbanarc.Admin.POJOCLASS;

public class POJOAdmingetAllFeedback {
    String id,username,image,message,date,time;

    public POJOAdmingetAllFeedback(String id, String username, String image, String message, String date, String time) {
        this.id = id;
        this.username = username;
        this.image = image;
        this.message = message;
        this.date = date;
        this.time = time;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
