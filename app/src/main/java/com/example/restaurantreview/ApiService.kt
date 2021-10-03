package com.example.restaurantreview

import retrofit2.Call
import retrofit2.http.*

interface ApiService { // interface yang berisi kumpulan endpoint yang digunakan pada sebuah aplikasi
    @GET("detail/{id}")
    fun getRestaurant(
        // @Path untuk mengakses detail suatu restoran dengan URL
        @Path("id") id: String
    ): Call<RestaurantResponse>

    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("review") review: String
    ): Call<PostReviewResponse>
}