package com.example.foodyreviewbe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopResponse {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("shop")
    @Expose
    private Shop shop;

    public ShopResponse(String id, String createdAt, String userId, Shop shop) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.shop = shop;
    }

    public  ShopResponse() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
