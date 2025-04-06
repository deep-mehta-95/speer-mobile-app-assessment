package com.dm.githubuserassesment.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dm.githubuserassesment.R;
import com.dm.githubuserassesment.others.GitHubViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class SearchFragment extends Fragment {

    private TextInputEditText usernameInput;
    private CardView cardView;
    private TextView errorText, nameText, usernameText, bioText, followersText, followingText;
    private ImageView avatar;
    private GitHubViewModel viewModel;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        usernameInput = view.findViewById(R.id.username_input);
        cardView = view.findViewById(R.id.card_view);
        errorText = view.findViewById(R.id.error_text);
        avatar = view.findViewById(R.id.avatar);
        nameText = view.findViewById(R.id.name_text);
        usernameText = view.findViewById(R.id.username_text);
        bioText = view.findViewById(R.id.bio_text);
        followersText = view.findViewById(R.id.followers_text);
        followingText = view.findViewById(R.id.following_text);

        viewModel = new ViewModelProvider(requireActivity()).get(GitHubViewModel.class);

        Button searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            String username = Objects.requireNonNull(usernameInput.getText()).toString().trim();
            if (!username.isEmpty()) {
                viewModel.fetchUser(username);
            }
        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                errorText.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);

                Glide.with(requireContext()).load(user.avatarUrl).into(avatar);
                nameText.setText(user.name != null ? user.name : "");
                usernameText.setText("@" + user.login);
                bioText.setText(user.bio != null ? user.bio : "");

                // Follower Count
                followersText.setText(user.followers + " Followers");
                followersText.setOnClickListener(v -> {
                    viewModel.fetchFollowers(user.login);
                    // Navigate to FollowersFragment
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new FollowersFragment())
                            .addToBackStack(null)
                            .commit();
                });

                // Following Count
                followingText.setText(user.following + " Following");
                followingText.setOnClickListener(v -> {
                    viewModel.fetchFollowing(user.login);
                    // Navigate to FollowingFragment
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new FollowingFragment())
                            .addToBackStack(null)
                            .commit();
                });
            }
        });

        // No User Found
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                errorText.setText(error);
                errorText.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.GONE);
            }
        });
    }
}