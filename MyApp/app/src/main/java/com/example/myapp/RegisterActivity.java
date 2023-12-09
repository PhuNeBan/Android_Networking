package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.databinding.ActivityRegisterBinding;
import com.example.myapp.helpers.IRetrofitRouter;
import com.example.myapp.helpers.RetrofitHelper;
import com.example.myapp.models.LoginResponseModel;
import com.example.myapp.models.RegisterResponseModel;
import com.example.myapp.models.UserModel;
import com.example.myapp.models.UserRegisterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    IRetrofitRouter iRetrofitRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);

        setOnClick();
    }

    private void setOnClick() {
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.textFieldEmail.getEditText().getText().toString();
                String password = binding.textFieldPassword.getEditText().getText().toString();
                String confirmPassword = binding.textFieldConfirmPw.getEditText().getText().toString();
                String name = binding.textFieldName.getEditText().getText().toString();
                String phone = binding.textFieldPhone.getEditText().getText().toString();
                String address = binding.textFieldAddress.getEditText().getText().toString();

                UserRegisterModel userRegisterModel = new UserRegisterModel();
                userRegisterModel.setEmail(email);
                userRegisterModel.setPassword(password);
                userRegisterModel.setConfirmPassword(confirmPassword);
                userRegisterModel.setName(name);
                userRegisterModel.setPhone(phone);
                userRegisterModel.setAddress(address);
                userRegisterModel.setRole("user"); //mac dinh user

                iRetrofitRouter.register(userRegisterModel).enqueue(registerCallback);
            }
        });

        //gologin
        binding.txtGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    Callback<RegisterResponseModel> registerCallback = new Callback<RegisterResponseModel>() {
        @Override
        public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
            if (response.isSuccessful()){
                RegisterResponseModel registerResponseDTO = response.body();
                if (registerResponseDTO.getStatus()) {
                    // nếu thành công chuyển sang màn hình login
                    Toast.makeText(RegisterActivity.this,
                                    "successfully!!!", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this,
                                    "Failed!!!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

        @Override
        public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
            Log.d(">>> register", "onFailure: " + t.getMessage());
        }
    };
}