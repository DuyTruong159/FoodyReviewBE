package com.example.foodyreviewbe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ImageView imgBack, imgHidePass;
    Spinner spinnerGender;
    EditText password, passwordConfirm, nickname, email;
    Button btn_register;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        imgBack = (ImageView) findViewById(R.id.img_back);
        imgHidePass = (ImageView) findViewById(R.id.img_hide_pass);
        password = (EditText) findViewById(R.id.password);
        passwordConfirm = (EditText) findViewById(R.id.passwordConfirm);
        nickname = (EditText) findViewById(R.id.nickname);
        email = (EditText) findViewById(R.id.email);
        spinnerGender = (Spinner) findViewById(R.id.spinner_gender);
        btn_register = (Button) findViewById(R.id.btn_dky);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
                Log.d("MainActivity", item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("MainActivity", "Nothing");
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIService apiService = APIUntils.getSOService();

                String valueNickname = nickname.getText().toString().trim();
                String valueEmail = email.getText().toString().trim();
                String valuePassword = password.getText().toString().trim();
                String valuePasswordConf = passwordConfirm.getText().toString().trim();

                if(!TextUtils.isEmpty(valueNickname) &&
                        !TextUtils.isEmpty(valueEmail) &&
                        !TextUtils.isEmpty(valuePassword)) {
                    if(valuePassword.equals(valuePasswordConf)) {


                        apiService.register(valueNickname, item, valueEmail, valuePassword).enqueue(new Callback<com.example.foodyreviewbe.Response>() {
                            @Override
                            public void onResponse(Call<com.example.foodyreviewbe.Response> call, Response<com.example.foodyreviewbe.Response> response) {
                                com.example.foodyreviewbe.Response registerResponse = response.body();
                                Toast.makeText(getBaseContext(), registerResponse.getMessage(), Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<com.example.foodyreviewbe.Response> call, Throwable t) {
                                Log.d("MainActivity", t.toString());
                            }
                        });
                    } else {
                        Toast.makeText(getBaseContext(), "Password không đúng !!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Không được để trống !!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}