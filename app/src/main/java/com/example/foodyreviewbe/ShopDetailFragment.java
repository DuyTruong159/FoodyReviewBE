package com.example.foodyreviewbe;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;


public class ShopDetailFragment extends Fragment {

    View view;
    ImageView imageView, goBack;
    TextView txtName, txtDescription;
    ListView listView;
    ProgressBar process;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shop_detail, container, false);
        imageView = (ImageView) view.findViewById(R.id.img_shop);
        goBack = (ImageView) view.findViewById(R.id.img_back);
        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtDescription = (TextView) view.findViewById(R.id.txt_description);
        listView = (ListView) view.findViewById(R.id.lstView_review);
        process = view.findViewById(R.id.progress);
        linearLayout = (LinearLayout) view.findViewById(R.id.shop_detail);

        try {
            InputStream is = (InputStream) new URL(ShopFragment.shop.getImage()).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            imageView.setImageDrawable(d);

            txtName.setText(ShopFragment.shop.getName());
            txtDescription.setText(ShopFragment.shop.getDescription());
        } catch (Exception ex) {
            Log.d("Error", ex.getLocalizedMessage());
        }


        return view;
    }
}