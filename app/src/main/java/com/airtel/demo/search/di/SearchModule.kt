package com.airtel.demo.search.di

import com.airtel.demo.search.network.APIHandler
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@SearchNavScope
@Module
class SearchModule {

    @Provides
    @SearchNavScope
    fun getAPIClient(retrofit: Retrofit): APIHandler {
        return retrofit.create(APIHandler::class.java)
    }
}