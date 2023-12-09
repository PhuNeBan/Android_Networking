package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapp.databinding.ActivityNewsDetail2Binding;
import com.example.myapp.helpers.IRetrofitRouter;
import com.example.myapp.helpers.RetrofitHelper;
import com.example.myapp.models.GetNewsDetailModel;
import com.example.myapp.models.GetNewsResponseModel;
import com.example.myapp.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity2 extends AppCompatActivity {

    ActivityNewsDetail2Binding binding;

    IRetrofitRouter iRetrofitRouter;

    private Integer newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetail2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);

        setToolBar();

        if(getIntent().hasExtra("news")){
            NewsModel news = (NewsModel) getIntent().getSerializableExtra("news");
            Log.d("news",news.getTitle());
            if (news != null) {
                binding.tvTitleDetail.setText(news.getTitle());
                binding.tvContentDetail.setText(news.getContent());
                binding.tvCreatedDateDetail.setText(news.getCreated_at().toString());
                newsId = news.getId();

//                 gán ảnh từ url
                if(news.getImage() != null){
                    try {
                        String originalUrl = news.getImage();
                        String formattedUrl = URLEncoder.encode(originalUrl, "UTF-8");
                        String encodedUrl = formattedUrl.replace("%3A", ":")
                                .replace("%2F", "/")
                                .replace("+", "%20");
                        Picasso.get().load(encodedUrl).into(binding.ivImageDetail);
                        Log.d("imgDetail", "newsDetail: " + encodedUrl);
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("imgDetail", "newsDetail: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (newsId != null){
            getNewsDetail();
        }
    }

    private void getNewsDetail(){
        iRetrofitRouter.getNewsDetail(newsId.toString()).enqueue(getNewsDetailCallback);
    }

    Callback<GetNewsDetailModel> getNewsDetailCallback = new Callback<GetNewsDetailModel>() {
        @Override
        public void onResponse(Call<GetNewsDetailModel> call, Response<GetNewsDetailModel> response) {
            if (response.isSuccessful()){
                GetNewsDetailModel getNewsDetailModel = response.body();
                Log.d("NewsDetail", "onResponse: "+ getNewsDetailModel.getNews().getAuthor_name());
                binding.tvAuthorDetail.setText(getNewsDetailModel.getNews().getAuthor_name());
            }
        }

        @Override
        public void onFailure(Call<GetNewsDetailModel> call, Throwable t) {
            Log.d(">>> getNewsDetail", "onFailure: " + t.getMessage());
        }
    };

    private void setToolBar() {
        //setup toolbar
        setSupportActionBar(binding.toolBarNewsDetailActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBarNewsDetailActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.toolBarNewsDetailActivity.setTitleTextColor(Color.WHITE);
        Intent intent = getIntent();
        if (intent.hasExtra("news")){
            NewsModel news = (NewsModel) getIntent().getSerializableExtra("news");
            if (news != null) {
                getSupportActionBar().setTitle(news.getTitle());
            }
        }
    }

}