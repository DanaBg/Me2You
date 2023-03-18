package com.example.me2you;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.me2you.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
//        binding.dateEditBtn.setOnClickListener((view)->{
//            Dialog dialog = new DatePickerDialog(getContext(),(datePicker,yy,mm,dd)->{
//                y = yy;
//                m = mm;
//                d = dd;
//                setDate();
//            },y,m,d);
//            dialog.show();
//        });

//
//        binding.dateInputEt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
//                    Dialog dialog = new DatePickerDialog(getContext(),(datePicker,yy,mm,dd)->{
//                        y = yy;
//                        m = mm;
//                        d = dd;
//                        setDate2();
//                    },y,m,d);
//                    dialog.show();
//                    return true;
//                }
//                return false;
//            }
//        });
        return binding.getRoot();
    }
}