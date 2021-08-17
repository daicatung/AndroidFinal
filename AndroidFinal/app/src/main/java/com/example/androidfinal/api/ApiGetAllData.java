package com.example.androidfinal.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiGetAllData {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    // https://api.themoviedb.org/3/movie/popular?api_key=e7631ffcb8e766993e5ec0c1f4245f93&page={1}

    ApiGetAllData API_GET_ALL_DATA = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiGetAllData.class);


    @GET("3/movie/popular")
    Call<ApiGetListMovie> getMovieApi(@Query("api_key") String api_key,
                                      @Query("page") int page);

    @GET("/3/movie/{id}/credits?api_key=e7631ffcb8e766993e5ec0c1f4245f93")
    Call<ApiGetListCast> getCastApi(@Path("id") int id);

}
