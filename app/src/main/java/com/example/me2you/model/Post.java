package com.example.me2you.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.me2you.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String id="";
    public String userId="";
    public String location="";
    public String description="";
    public String pictureUrl="";
    public String itemType="";
    public String phoneNumber="";
    public Long updateTime;

    public Post(){
    }
    public Post( String id,String userId, String pictureUrl, String location, String itemType, String description, String phoneNumber) {
        this.userId = userId;
        this.id = id;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.itemType = itemType;
        this.phoneNumber = phoneNumber;
    }

    static final String USER_ID = "userId";
    static final String ID = "id";
    static final String PICTURE_URL = "pictureUrl";
    static final String DESCRIPTION = "description";
    static final String ITEM_TYPE = "itemType";
    static final String COLLECTION = "items";
    static final String LOCATION = "location";
    static final String PHONE_NUM = "phoneNumber";
    static final String UPDATE_TIME = "updateTime";
    static final String LOCAL_UPDATE_TIME = "itemsLocalUpdateTime";

    public static Post fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String userId = (String)json.get(USER_ID);
        String pictureUrl = (String)json.get(PICTURE_URL);
        String location = (String)json.get(LOCATION);
        String itemType = (String)json.get(ITEM_TYPE);
        String description = (String)json.get(DESCRIPTION);
        String phoneNumber = (String)json.get(PHONE_NUM);
        Post newPost = new Post(id,userId,pictureUrl,location,itemType,description,phoneNumber);
        try{
            Timestamp time = (Timestamp) json.get(UPDATE_TIME);
            newPost.setUpdateTime(time.getSeconds());
        }catch(Exception e){

        }
        return newPost;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_UPDATE_TIME, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_UPDATE_TIME,time);
        editor.commit();
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(USER_ID, getUserId());
        json.put(DESCRIPTION, getDescription());
        json.put(ITEM_TYPE, getItemType());
        json.put(LOCATION, getLocation());
        json.put(PICTURE_URL, getPictureUrl());
        json.put(UPDATE_TIME, FieldValue.serverTimestamp());
        return json;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
    public void setUserId(@NonNull String userId) { this.userId = userId; }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getItemType() {
        return itemType;
    }

    public String getLocation() {
        return location;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public Long getUpdateTime() {
        return updateTime;
    }

}
