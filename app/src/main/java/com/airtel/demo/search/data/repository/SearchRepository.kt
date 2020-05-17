package com.airtel.demo.search.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airtel.demo.R
import com.airtel.demo.base.utils.Fail
import com.airtel.demo.base.utils.Result
import com.airtel.demo.base.utils.Success
import com.airtel.demo.search.data.model.SearchResponse
import com.airtel.demo.search.network.APIHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


class SearchRepository @Inject constructor() {

    @Inject
    lateinit var apiHandler: APIHandler

    private var searchResponse = MutableLiveData<Result<SearchResponse>>()

    fun getSearchData(queryString: String, city: String) {
        apiHandler.getSearchList(queryString, city).enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {

                if (t is UnknownHostException
                    || t is SocketTimeoutException
                ) {
                    searchResponse.value = Fail(R.string.NO_INTERNET_CONNECTION)
                } else {
                    searchResponse.value = Fail(R.string.SERVER_ERROR)
                }
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                searchResponse.value = Success(response.body() as SearchResponse)

            }
        })
    }

    fun getSearchLiveData(): LiveData<Result<SearchResponse>> {
        return searchResponse
    }

}