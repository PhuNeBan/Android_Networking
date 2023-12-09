package com.example.myapp.models;

import java.util.List;

public class GetNewsResponseModel {
    private List<NewsModel> news;

    public GetNewsResponseModel() {
    }

    public List<NewsModel> getNews() {
        return news;
    }

    public void setNews(List<NewsModel> listNews) {
        this.news = listNews;
    }
}
