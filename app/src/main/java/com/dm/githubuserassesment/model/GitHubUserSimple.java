package com.dm.githubuserassesment.model;

import com.google.gson.annotations.SerializedName;

public class GitHubUserSimple {
    @SerializedName("login")
    public String login;

    @SerializedName("avatar_url")
    public String avatarUrl;
}
