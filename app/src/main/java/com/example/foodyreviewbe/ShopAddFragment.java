package com.example.foodyreviewbe;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;


public class ShopAddFragment extends Fragment {

    View view;
    EditText name, des, img;
    Button btn_add;
    ImageView imgBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shop_add, container, false);
        name = (EditText) view.findViewById(R.id.name);
        des = (EditText) view.findViewById(R.id.description);
        img = (EditText) view.findViewById(R.id.image);
        imgBack = (ImageView) view.findViewById(R.id.img_back);
        btn_add = (Button) view.findViewById(R.id.btn_add);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.LOGIN, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String id = sharedPreferences.getString("id", "");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(name.getText().toString().trim()) && !TextUtils.isEmpty(des.getText().toString().trim())
                        && !TextUtils.isEmpty(img.getText().toString().trim())) {

                    APIService apiService = APIUntils.getSOService();
                    apiService.addShop(id, name.getText().toString().trim(), des.getText().toString().trim(),
                            img.getText().toString().trim()).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            Log.d("Add", "true");
                            Log.d("Add", id);
                            try {
                                Log.d("Add", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                            if(response.isSuccessful()) {
//                                Log.d("Add", "true in");
//                                Log.d("Add in", id);
//                                loadFragment(new ShopFragment());
//
//                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Log.d("MainActivity", t.toString());
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Không được để trống !!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}