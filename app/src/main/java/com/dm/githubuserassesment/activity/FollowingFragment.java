package com.dm.githubuserassesment.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dm.githubuserassesment.R;
import com.dm.githubuserassesment.others.GitHubViewModel;
import com.dm.githubuserassesment.others.UserListAdapter;

public class FollowingFragment extends Fragment {

    private UserListAdapter adapter;
    private GitHubViewModel viewModel;

    public FollowingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.user_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new UserListAdapter(username -> {
            viewModel.fetchUser(username);
            requireActivity().getSupportFragmentManager()
                    .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); // Clears stack
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(GitHubViewModel.class);

        viewModel.getFollowing().observe(getViewLifecycleOwner(), users -> {
            if (users != null) adapter.setUserList(users);
        });
    }
}