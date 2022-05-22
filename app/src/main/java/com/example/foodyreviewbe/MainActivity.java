package com.example.foodyreviewbe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText password, email;
    ImageView imgHidePass;
    Button btnDky, btnLogin;

    static final String LOGIN = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        imgHidePass = (ImageView) findViewById(R.id.img_hide_pass);
        btnDky = (Button) findViewById(R.id.btn_dky);
        btnLogin = (Button) findViewById(R.id.btn_login);

        imgHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    imgHidePass.setImageResource(R.drawable.invisible);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    imgHidePass.setImageResource(R.drawable.visible);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIService apiService = APIUntils.getSOService();
                String valueEmail = email.getText().toString().trim();
                String valuePass = password.getText().toString().trim();
                if(!TextUtils.isEmpty(valueEmail) && !TextUtils.isEmpty(valuePass)){
                    apiService.login(valueEmail, valuePass).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.body().getCode() == 200 && response.body().getUser().getRole().equals("SHOP_OWNER")) {
                                Log.d("login", response.body().getUser().getRole());
                                LoginResponse user = response.body();
                                SharedPreferences preferencesId = getBaseContext().getSharedPreferences(LOGIN,0);
                                SharedPreferences.Editor editorId = preferencesId.edit();
                                editorId.putString("id", user.getUser().getId());
                                editorId.commit();

                                @SuppressLint("WrongConstant") SharedPreferences preferencesName = getBaseContext().getSharedPreferences(LOGIN, 1);
                                SharedPreferences.Editor editorName = preferencesName.edit();
                                editorName.putString("nickname", user.getUser().getNickName());
                                editorName.commit();

                                @SuppressLint("WrongConstant") SharedPreferences preferencesGender = getBaseContext().getSharedPreferences(LOGIN, 2);
                                SharedPreferences.Editor editorGender = preferencesGender.edit();
                                editorGender.putString("gender", user.getUser().getGender());
                                editorGender.commit();

                                @SuppressLint("WrongConstant") SharedPreferences preferencesUsername = getBaseContext().getSharedPreferences(LOGIN,3);
                                SharedPreferences.Editor editorUsername = preferencesUsername.edit();
                                editorUsername.putString("username", user.getUser().getEmail());
                                editorUsername.commit();

                                @SuppressLint("WrongConstant") SharedPreferences preferencesPassword = getBaseContext().getSharedPreferences(LOGIN,4);
                                SharedPreferences.Editor editorPassword = preferencesPassword.edit();
                                editorPassword.putString("password", user.getUser().getPassword());
                                editorPassword.commit();

                                Toast.makeText(getBaseContext(), user.getMessage(), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), "Something wrong. Please check again!!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.d("MainActivity", t.toString());
                        }
                    });
                } else {
                    Toast.makeText(getBaseContext(), "Something wrong. Please check again!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }
}