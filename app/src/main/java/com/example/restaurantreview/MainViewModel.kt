package com.example.restaurantreview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    /*
    bedanya MutableLiveData bisa kita ubah value-nya, sedangkan LiveData bersifat read-only (tidak dapat diubah)

    Inilah yang disebut dengan encapsulation pada LiveData, yaitu dengan membuat data yang bertipe
    MutableLiveData menjadi private (_listReview) dan yang bertipe LiveData menjadi public (listReview).
    Cara ini disebut dengan backing property. Dengan begitu Anda dapat mencegah variabel yang bertipe
    MutableLiveData diubah dari luar class. Karena memang seharusnya hanya ViewModel-lah yang dapat
    mengubah data.
     */
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant
    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        /*
        "_isLoading.setValue(true)"
        Kita bisa menyisipkan perubahan yang terjadi dengan setValue().
        Jadi secara realtime MutableLiveData akan menerima data yang baru
         */
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        /*
        ungsi enqueue untuk menjalankan request secara asynchronous di background. Sehingga aplikasi tidak freeze/lag ketika melakukan request
         */
        client.enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    // datanya dapat diambil di response.body()
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _restaurant.value = response.body()?.restaurant
                        _listReview.value = response.body()?.restaurant?.customerReviews
                    }

                    /*
                     kita tidak perlu melakukan parsing lagi. proses parsing dilakukan secara otomatis oleh Retrofit dengan menggunakan kode
                    .addConverterFactory(GsonConverterFactory.create()) di bagian ApiConfig dan anotasi SerializedName pada masing-masing POJO
                     */

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun postReview(review: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)
        client.enqueue(object : Callback<PostReviewResponse> {
            override fun onResponse(call: Call<PostReviewResponse>, response: Response<PostReviewResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listReview.value = response.body()?.customerReviews
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}