package com.example.foodyreviewbe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class SettingFragment extends Fragment {

    View view;
    TextView txt_id, txt_nickname, txt_gender, txt_email;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_setting, container, false);
        button = view.findViewById(R.id.logout);
        txt_id = view.findViewById(R.id.id);
        txt_nickname = view.findViewById(R.id.nickname);
        txt_gender = view.findViewById(R.id.gender);
        txt_email = view.findViewById(R.id.email);

        SharedPreferences preferencesId = getContext().getSharedPreferences(MainActivity.LOGIN, 0);
        SharedPreferences.Editor editorId = preferencesId.edit();
        String id = preferencesId.getString("id", "");

        @SuppressLint("WrongConstant") SharedPreferences preferencesName = getContext().getSharedPreferences(MainActivity.LOGIN, 1);
        SharedPreferences.Editor editorName = preferencesName.edit();
        String name = preferencesName.getString("nickname", "");

        @SuppressLint("WrongConstant") SharedPreferences preferencesGender = getContext().getSharedPreferences(MainActivity.LOGIN, 2);
        SharedPreferences.Editor editorGender = preferencesGender.edit();
        String gender = preferencesGender.getString("gender", "");

        @SuppressLint("WrongConstant") SharedPreferences preferencesUsername = getContext().getSharedPreferences(MainActivity.LOGIN, 3);
        SharedPreferences.Editor editorUsername = preferencesUsername.edit();
        String email = preferencesUsername.getString("username", "");

        txt_id.setText(id);
        txt_nickname.setText(name);
        txt_gender.setText(gender);
        txt_email.setText(email);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editorId.remove("id");
                editorId.apply();

                editorName.remove("nickname");
                editorName.apply();

                editorGender.remove("gender");
                editorGender.apply();

                editorUsername.remove("username");
                editorUsername.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;
    }
}