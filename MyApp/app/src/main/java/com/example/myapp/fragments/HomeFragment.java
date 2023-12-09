package com.example.myapp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.adapters.NewsAdapter;
import com.example.myapp.databinding.FragmentHomeBinding;
import com.example.myapp.helpers.IRetrofitRouter;
import com.example.myapp.helpers.RetrofitHelper;
import com.example.myapp.models.GetNewsResponseModel;
import com.example.myapp.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private List<NewsModel> data;

    private NewsAdapter newsAdapter;

    IRetrofitRouter iRetrofitRouter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    // method này là thể hiện của lớp, dùng để gọi trực tiếp mà ko cần tạo mới đối tượng.
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    // xử lý sự kiện trong method bên dưới.

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);

        data = new ArrayList<>();

        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        binding.RVNews.setLayoutManager(linearLayoutManager);


        newsAdapter = new NewsAdapter(getContext(),data);
        binding.RVNews.setAdapter(newsAdapter);
        binding.RVNews.setHasFixedSize(true);

        GetNews();

    }

    private void GetNews(){
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String token = pref.getString("TOKEN","");
        Log.d("token", "GetNews: " + token);
        iRetrofitRouter.getNews("Bearer "+ token).enqueue(getNewsCallback);
    }

    Callback<GetNewsResponseModel> getNewsCallback = new Callback<GetNewsResponseModel>() {
        @Override
        public void onResponse(Call<GetNewsResponseModel> call, Response<GetNewsResponseModel> response) {
            if (response.isSuccessful()){
                GetNewsResponseModel getNewsResponseModel = response.body();
                Log.d("News", "onResponse: "+ getNewsResponseModel.getNews().size());
                data.clear();
                data.addAll(getNewsResponseModel.getNews());
//                Log.d("News", "onResponse: "+ data.get(0).getTitle());
                newsAdapter.notifyDataSetChanged(); // cập nhật lại dữ liệu cho adapter
            }
        }

        @Override
        public void onFailure(Call<GetNewsResponseModel> call, Throwable t) {
            Log.d(">>> getNews", "onFailure: " + t.getMessage());
        }
    };
}