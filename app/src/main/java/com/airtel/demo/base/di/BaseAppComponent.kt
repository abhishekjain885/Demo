package com.airtel.demo.base.di

import android.content.Context
import com.airtel.demo.base.qualifier.ApplicationContext
import com.google.gson.Gson
import dagger.Component
import retrofit2.Retrofit

@ApplicationScope
@Component(modules = [AppModule::class])
interface BaseAppComponent {

    @ApplicationContext
    fun getContext(): Context?

    fun retrofitBuilder(): Retrofit

    fun gson(): Gson?
}