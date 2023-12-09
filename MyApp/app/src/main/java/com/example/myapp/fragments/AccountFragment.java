package com.example.myapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;
import com.example.myapp.databinding.FragmentAccountBinding;
import com.example.myapp.models.UserModel;

import java.io.Serializable;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    private UserModel user;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAccountBinding.bind(view);

        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null){
            user = (UserModel) bundle.getSerializable("user");
            if (user != null){
                binding.tvName.setText(user.getName());
                binding.tvEmail.setText(user.getEmail());
                binding.tvPhone.setText(user.getPhone());
                binding.tvAddress.setText(user.getAddress());
                binding.tvRole.setText(user.getRole());
//                Log.d("user",user.getName());
            }else {
                Log.d("user","null");
            }
        }else {
            Log.d("bundle","null");
        }

    }
}