package com.example.me2you;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.me2you.model.Model;
import com.example.me2you.model.Student;

import java.util.List;

public class StudentsListFragmentViewModel extends ViewModel {
    private LiveData<List<Student>> data = Model.instance().getAllStudents();

    LiveData<List<Student>> getData(){
        return data;
    }
}
