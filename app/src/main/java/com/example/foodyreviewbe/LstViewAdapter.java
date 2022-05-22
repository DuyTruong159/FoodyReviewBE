package com.example.foodyreviewbe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LstViewAdapter extends BaseAdapter implements Filterable {

    Context context;
    List<Shop> shopList;

    ImageView imageView;
    TextView name, description;

    public void setNormal(List<Shop> shops) {
        this.shopList = shops;
    }

    public LstViewAdapter(Context context, List<Shop> list) {
        this.context = context;
        this.shopList = list;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int i) {
        return shopList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.shop_items, null);
        imageView = (ImageView) view1.findViewById(R.id.img_shop);
        name = (TextView) view1.findViewById(R.id.name_shop);
        description = (TextView) view1.findViewById(R.id.des_shop);

        try {
            InputStream is = (InputStream) new URL(shopList.get(i).getImage()).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        name.setText(shopList.get(i).getName());
        description.setText(shopList.get(i).getDescription());

        return view1;
    }

    public void updateAnswers(List<Shop> items) {
        this.shopList = items;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                shopList = (ArrayList<Shop>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Shop> FilteredArrList = new ArrayList<Shop>();

                if (shopList == null) {
                    shopList = new ArrayList<Shop>(shopList);
                }

                if (constraint == null || constraint.length() == 0) {
                    results.count = shopList.size();
                    results.values = shopList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < shopList.size(); i++) {
                        String data = shopList.get(i).getName();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(new Shop(shopList.get(i).getName(),shopList.get(i).getImage(),shopList.get(i).getDescription()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
