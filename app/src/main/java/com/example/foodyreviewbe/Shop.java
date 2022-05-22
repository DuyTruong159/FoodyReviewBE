package com.example.foodyreviewbe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("shopName")
    @Expose
    private String name;

    @SerializedName("logoUrl")
    @Expose
    private String image;

    @SerializedName("description")
    @Expose
    private String description;

    public Shop(String id, String name, String image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Shop(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    protected Shop(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        description = in.readString();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(description);
    }
}
