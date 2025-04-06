package com.dm.githubuserassesment.others;

import com.dm.githubuserassesment.model.GitHubUser;
import com.dm.githubuserassesment.model.GitHubUserSimple;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubApi {

    @GET("users/{username}")
    Call<GitHubUser> getUser(@Path("username") String username);

    @GET("users/{username}/followers")
    Call<List<GitHubUserSimple>> getFollowers(@Path("username") String username);

    @GET("users/{username}/following")
    Call<List<GitHubUserSimple>> getFollowing(@Path("username") String username);
}
