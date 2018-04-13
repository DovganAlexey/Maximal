package com.enzo.testaufgabe;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by enzo on 11.04.18.
 */

public class CustomApplication extends Application {

    private static RetrofitApi retrofitApi;
    private Retrofit retrofit;
    private static DBHelper dbHelper;
    private static SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApi = retrofit.create(RetrofitApi.class);
        dbHelper = new DBHelper(this);
        sharedPref = getApplicationContext().getSharedPreferences("shared_prefs_file", Context.MODE_PRIVATE);
    }

    public static RetrofitApi getApi() {
        return retrofitApi;
    }

    public static DBHelper getDBHelper() {
        return dbHelper;
    }

    public static SharedPreferences getSharedPrefs() {
        return sharedPref;
    }
}