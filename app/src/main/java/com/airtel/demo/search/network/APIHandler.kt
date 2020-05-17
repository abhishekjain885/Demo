package com.airtel.demo.search.network

import com.airtel.demo.search.data.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIHandler {

    @GET(ENDPOINT_SEARCH)
    fun getSearchList(
        @Query("queryString") queryString: String,
        @Query("city") city: String
    ): Call<SearchResponse>

    companion object {
        const val ENDPOINT_SEARCH = "/compassLocation/rest/address/autocomplete"
    }
}