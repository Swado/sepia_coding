package com.example.sepia_coding;

public class petObject {
    String image_url, title, content_url, date_added;

    petObject(String image_url, String title, String content_url, String date_added) {
        this.image_url = image_url;
        this.content_url = content_url;
        this.title = title;
        this.date_added = date_added;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}