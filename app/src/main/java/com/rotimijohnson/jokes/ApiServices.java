package com.rotimijohnson.jokes;

import com.rotimijohnson.jokes.models.jokes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("joke/Any?type=single")
    Call<jokes> getRandomJoke();

    @GET("joke/Any?type=single")
    Call<jokes> getJoke(
            @Query("contains") String jokeText
    );
}
