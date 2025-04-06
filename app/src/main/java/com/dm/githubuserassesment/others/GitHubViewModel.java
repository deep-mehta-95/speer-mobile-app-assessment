package com.dm.githubuserassesment.others;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dm.githubuserassesment.model.GitHubUser;
import com.dm.githubuserassesment.model.GitHubUserSimple;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitHubViewModel extends ViewModel {

    private final MutableLiveData<GitHubUser> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GitHubUserSimple>> followersLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GitHubUserSimple>> followingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private final GitHubApi api = ApiClient.getApi();

    public LiveData<GitHubUser> getUser() {
        return userLiveData;
    }

    public LiveData<List<GitHubUserSimple>> getFollowers() {
        return followersLiveData;
    }

    public LiveData<List<GitHubUserSimple>> getFollowing() {
        return followingLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    // Fetch the user data
    public void fetchUser(String username) {
        api.getUser(username).enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(@NonNull Call<GitHubUser> call, @NonNull Response<GitHubUser> response) {
                if (response.isSuccessful()) {
                    userLiveData.postValue(response.body());
                    errorLiveData.postValue(null);
                } else {
                    userLiveData.postValue(null);
                    errorLiveData.postValue("User not found");
                }
            }

            @Override
            public void onFailure(@NonNull Call<GitHubUser> call, @NonNull Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    // Fetch user's followers
    public void fetchFollowers(String username) {
        api.getFollowers(username).enqueue(new Callback<List<GitHubUserSimple>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitHubUserSimple>> call, @NonNull Response<List<GitHubUserSimple>> response) {
                if (response.isSuccessful()) {
                    followersLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GitHubUserSimple>> call, @NonNull Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    // Fetch user's following
    public void fetchFollowing(String username) {
        api.getFollowing(username).enqueue(new Callback<List<GitHubUserSimple>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitHubUserSimple>> call, @NonNull Response<List<GitHubUserSimple>> response) {
                if (response.isSuccessful()) {
                    followingLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GitHubUserSimple>> call, @NonNull Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}
