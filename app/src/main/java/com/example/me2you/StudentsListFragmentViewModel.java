package com.example.me2you;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.me2you.model.Model;
import com.example.me2you.model.Post;

import java.util.List;

public class StudentsListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getAllPosts();

    LiveData<List<Post>> getData(){
        return data;
    }
}
