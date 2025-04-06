package com.dm.githubuserassesment.model;

import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    @SerializedName("login")
    public String login;

    @SerializedName("name")
    public String name;

    @SerializedName("avatar_url")
    public String avatarUrl;

    @SerializedName("bio")
    public String bio;

    @SerializedName("followers")
    public int followers;

    @SerializedName("following")
    public int following;
}
