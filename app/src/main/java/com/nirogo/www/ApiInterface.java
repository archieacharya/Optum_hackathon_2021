package com.nirogo.www;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by haerul on 17/03/18.
 */

public interface ApiInterface {


    @GET("shop_list_search.php")
    Call<List<SearchShopList>> getShopList(
            @Query("lat1") String lat,
            @Query("lon1") String lon,
            @Query("key") String keyword
    );
}
