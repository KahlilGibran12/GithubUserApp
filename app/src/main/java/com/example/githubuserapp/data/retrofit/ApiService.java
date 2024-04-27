package com.example.githubuserapp.data.retrofit;

import com.example.githubuserapp.data.response.DetailResponse;
import com.example.githubuserapp.data.response.UsernewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search/users")
    @Headers({"Authorization: token" + TOKEN})
    Call<UsernewResponse> getUsers(@Query("q") String username);

    @GET("users/{username}")
    @Headers({"Authorization: token" + TOKEN})
    Call<DetailResponse> getUserDetail(@Path("username") String username);

    String TOKEN = "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";


}
