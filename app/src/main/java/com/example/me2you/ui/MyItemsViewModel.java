package com.example.me2you.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.me2you.model.Model;
import com.example.me2you.model.Post;

import java.util.List;

public class MyItemsViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getUserPosts();

    public LiveData<List<Post>> getData(){
        data = Model.instance().getUserPosts();
        return data;
    }
}