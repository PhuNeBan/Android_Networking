package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.databinding.ActivityLoginBinding;
import com.example.myapp.fragments.AccountFragment;
import com.example.myapp.helpers.IRetrofitRouter;
import com.example.myapp.helpers.RetrofitHelper;
import com.example.myapp.models.ForgotPWResponseModel;
import com.example.myapp.models.LoginResponseModel;
import com.example.myapp.models.UserModel;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    IRetrofitRouter iRetrofitRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iRetrofitRouter = RetrofitHelper.createService(IRetrofitRouter.class);

        //btnlogin
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.txtEmail.getText().toString();
                String password = binding.textFieldPassword.getEditText().getText().toString();

                UserModel userModel = new UserModel();
                userModel.setEmail(email);
                userModel.setPassword(password);

                //gọi api login
                iRetrofitRouter.login(userModel).enqueue(loginCallback);
//                Log.d("btn",email+password);

            }
        });

        binding.btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.txtForgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.txtEmail.getText().toString();

                UserModel userModel = new UserModel();
                userModel.setEmail(email);

                //gọi api forgot password.
                iRetrofitRouter.forgotPassword(userModel).enqueue(forgotPWCallback);
            }
        });

    }

    Callback<LoginResponseModel> loginCallback = new Callback<LoginResponseModel>() {
        @Override
        public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
            if (response.isSuccessful()) {
                LoginResponseModel loginResponseDTO = response.body();
                if (loginResponseDTO.getStatus()) {
                    // nếu thành công chuyển sang màn hình danh sách
                    Toast.makeText(LoginActivity.this,
                                    "successfully!!!", Toast.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    //truyền user qua MainActivity
                    UserModel user = loginResponseDTO.getUser();
                    intent.putExtra("user", (Serializable) user);

                    startActivity(intent);

                    //lưu share preferences trạng thái login
                    boolean STT = binding.cbRememberPass.isChecked();
                    String PASSWORD = binding.textFieldPassword.getEditText().getText().toString();
                    String EMAIL = binding.txtEmail.getText().toString();
                    rememberUser(loginResponseDTO.getToken(), STT, PASSWORD, EMAIL);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                                    "Failed!!!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
        @Override
        public void onFailure(Call<LoginResponseModel> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };

    Callback<ForgotPWResponseModel> forgotPWCallback = new Callback<ForgotPWResponseModel>() {
        @Override
        public void onResponse(Call<ForgotPWResponseModel> call, Response<ForgotPWResponseModel> response) {
            if (response.isSuccessful()) {
                ForgotPWResponseModel forgotPWResponseModel = response.body();
                if (forgotPWResponseModel.getStatus()) {
                    // nếu thành công
                    Toast.makeText(LoginActivity.this,
                                    "sent email successfully!!!", Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(LoginActivity.this,
                                    "sent email failed!!!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        }

        @Override
        public void onFailure(Call<ForgotPWResponseModel> call, Throwable t) {
            Log.d(">>> forgot", "onFailure: " + t.getMessage());
        }
    };

    public void rememberUser(String TOKEN, boolean STT, String PASSWORD, String EMAIL){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!STT){
            //xoa tinh trang luu tru truoc do
            edit.clear();
        }else {
            //luu du lieu
            edit.putBoolean("REMEMBER",STT);
            edit.putString("PASSWORD",PASSWORD);
            edit.putString("EMAIL",EMAIL);
        }
            edit.putString("TOKEN",TOKEN);
        //luu lai toan bo
        edit.apply();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String email = pref.getString("EMAIL","");
        String password = pref.getString("PASSWORD","");
        Boolean save = pref.getBoolean("REMEMBER",false);
        if(save == true){
            binding.txtEmail.setText(email);
            binding.textFieldPassword.getEditText().setText(password);
            binding.cbRememberPass.setChecked(save);
        }
    }
}