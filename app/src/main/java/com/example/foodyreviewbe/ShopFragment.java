package com.example.foodyreviewbe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopFragment extends Fragment {

    View view;
    ListView lstView;
    EditText search;
    LstViewAdapter lstAdapter;
    ProgressBar process;
    ImageView add;

    List<Shop> shopList = new ArrayList<Shop>(0);

    public static Shop shop = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shop, container, false);
        search = view.findViewById(R.id.search);
        process = view.findViewById(R.id.progress);
        add = view.findViewById(R.id.add);
        lstView = view.findViewById(R.id.lstView_shop);

        lstAdapter = new LstViewAdapter(getActivity(), shopList);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.LOGIN, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String id = sharedPreferences.getString("id", "");

        APIService apiService = APIUntils.getSOService();
        apiService.getShop(id).enqueue(new Callback<List<ShopResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<ShopResponse>> call, Response<List<ShopResponse>> response) {
                if (response.isSuccessful()) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    for (int i = 0; i < response.body().size(); i++) {
                        shopList.add(response.body().get(i).getShop());
                    }

                    for (int i = 0, j = shopList.size() - 1; i < j; i++) {
                        shopList.add(i, shopList.remove(j));
                    }
                    List<Shop> shopList1 = shopList.stream().limit(response.body().size()).collect(Collectors.toList());
                    lstAdapter.updateAnswers(shopList1);
                    process.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ShopResponse>> call, Throwable t) {
                Log.d("MainActivity", t.toString());
            }
        });

        lstView.setAdapter(lstAdapter);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                shop = shopList.get(i);

                loadFragment(new ShopDetailFragment());
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(search.getText().toString().trim().length() > 0) {
                    lstAdapter.getFilter().filter(charSequence.toString());
                } else {
                    lstAdapter.setNormal(shopList);
                    lstAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ShopAddFragment());
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