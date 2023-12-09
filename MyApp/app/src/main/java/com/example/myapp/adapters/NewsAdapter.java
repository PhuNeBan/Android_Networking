package com.example.myapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.NewsDetailActivity2;
import com.example.myapp.databinding.ItemNews2Binding;
import com.example.myapp.models.NewsModel;

import java.io.Serializable;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<NewsModel> list;


    public NewsAdapter(Context context, List<NewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNews2Binding binding = ItemNews2Binding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel news = list.get(position);
        holder.binding.tvTitle2.setText(news.getTitle());

        // sự kiện click vào item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "onClick: " + news.getTitle());
                // gửi dữ liệu và chuyển màn hình qua NewsDetailActivity2
                Intent intent = new Intent(context, NewsDetailActivity2.class);
                intent.putExtra("news", (Serializable) news);
                context.startActivity(intent);
            }
        });


        // gán ảnh từ url
//        if(news.getImage() != null){
//            try {
//                String originalUrl = news.getImage();
//                String formattedUrl = URLEncoder.encode(originalUrl, "UTF-8");
//                String encodedUrl = formattedUrl.replace("%3A", ":")
//                        .replace("%2F", "/")
//                        .replace("+", "%20");
//                Picasso.get().load(encodedUrl).into(holder.binding.ivImage);
//                Log.d("img", "onBindViewHolder: " + encodedUrl);
//            }catch (Exception e){
//                e.printStackTrace();
//                Log.d("img", "onBindViewHolder: " + e.getMessage());
//            }
//        }else {
//            Picasso.get().load(R.drawable.user).into(holder.binding.ivImage);
//        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 :
                list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemNews2Binding binding;
        public ViewHolder(@NonNull ItemNews2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
