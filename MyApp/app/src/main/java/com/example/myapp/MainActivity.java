package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapp.fragments.AccountFragment;
import com.example.myapp.fragments.HomeFragment;
import com.example.myapp.databinding.ActivityMainBinding;
import com.example.myapp.models.UserModel;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        replaceFragment(new HomeFragment());

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int id = item.getItemId();
                if(id == R.id.home){
                    replaceFragment(new HomeFragment());
                }
                if(id == R.id.account){
                    if(getIntent().hasExtra("user")){
                        UserModel user = (UserModel) getIntent().getSerializableExtra("user");
                        showAccountFragment(user);
                    }else {
                        showAccountFragment(null);
                    }
                }
                return false;
            }
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }

    private void showAccountFragment(UserModel user){
        //truyền dữ liệu qua bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", (Serializable) user);

        //tạo instance của fragment và setArguments
        AccountFragment accountFragment = new AccountFragment();
        accountFragment.setArguments(bundle);

        //thay thế fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, accountFragment)
                .addToBackStack(null)
                .commit();
    }
}