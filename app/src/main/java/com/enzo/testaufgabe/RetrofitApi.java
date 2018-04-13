package com.enzo.testaufgabe;

import com.enzo.testaufgabe.models.Message;
import com.enzo.testaufgabe.models.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by enzo on 11.04.18.
 */

public interface RetrofitApi {

    @GET("users")
    Call<List<Person>> getUsers();

    @GET("posts")
    Call<List<Message>> getMessages(@Query("query") String query);

}
