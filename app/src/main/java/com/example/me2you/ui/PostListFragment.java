package com.example.me2you.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.me2you.R;
import com.example.me2you.databinding.FragmentPostListBinding;
import com.example.me2you.model.Model;
import com.example.me2you.model.Movie;
import com.example.me2you.model.MovieModel;

import java.util.List;

public class PostListFragment extends Fragment {
    FragmentPostListBinding binding;
    PostRecyclerAdapter adapter;
    PostListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);

        View addButton = view.findViewById(R.id.btnAdd);
        NavDirections action = PostListFragmentDirections.actionGlobalAddPostFragment();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });

        Model.instance().EventStudentsListLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(()->{
            reloadData();
        });

        LiveData<List<Movie>> data = MovieModel.instance.searchMoviesByTitle("avatar");
        data.observe(getViewLifecycleOwner(),list->{
            list.forEach(item->{

            });
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostListFragmentViewModel.class);
    }

    void reloadData(){
//        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().refreshAllPosts();
    }
}