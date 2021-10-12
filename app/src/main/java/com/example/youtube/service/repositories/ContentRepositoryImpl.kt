package com.example.youtube.service.repositories

import com.example.youtube.service.network.APIService
import com.example.youtube.service.models.VideoContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentRepositoryImpl(
    private val api: APIService
) : ContentRepository {
    override fun getVideoContents(onResult: (isSuccess: Boolean, response: ArrayList<VideoContent>?) -> Unit) {
        api.getVideoContents()
            .enqueue(object : Callback<ArrayList<VideoContent>> {
                override fun onResponse(call: Call<ArrayList<VideoContent>>,
                    response: Response<ArrayList<VideoContent>>?) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ArrayList<VideoContent>>, t: Throwable) {
                    onResult(false, null)
                }
            })
    }
}