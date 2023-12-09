package com.example.myapp.models;

import java.io.Serializable;

public class NewsModel implements Serializable {
//    "id": 3,
//    "title": "Tin tuc 3",
//    "content": "Noi dung tin tuc 3",
//    "image": null,
//    "created_at": "2023-11-20 16:43:31",
//    "author_id": 1,
//    "topic_id": 3,
//    "updated_at": "2023-11-20 16:43:31",
//    "updated_by": 1
    private Integer id,author_id, topic_id, updated_by ;
    private String title, content, image, created_at, updated_at;

    public NewsModel() {
    }

    public NewsModel(Integer id, Integer author_id, Integer topic_id, Integer updated_by, String title, String content, String image, String created_at, String updated_at) {
        this.id = id;
        this.author_id = author_id;
        this.topic_id = topic_id;
        this.updated_by = updated_by;
        this.title = title;
        this.content = content;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public Integer getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(Integer updated_by) {
        this.updated_by = updated_by;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
