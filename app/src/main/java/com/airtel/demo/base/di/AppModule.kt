package com.airtel.demo.base.di

import android.content.Context
import android.util.Log
import com.airtel.demo.base.qualifier.ApplicationContext
import com.airtel.demo.search.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class AppModule(context: Context) {

    private var context: Context? = null

    init {
        this.context = context
    }

    @ApplicationScope
    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return context!!.applicationContext
    }

    @ApplicationScope
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            // .setDateFormat(com.tokopedia.abstraction.common.di.module.net.RetrofitModule.GSON_DATE_FORMAT)
            .setPrettyPrinting()
            .serializeNulls()
            .create()
    }

    @ApplicationScope
    @Provides
    fun provideRetrofitBuilder(gson: Gson): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        val logging =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.e(
                    "API",
                    message
                )
            })
        logging.level = HttpLoggingInterceptor.Level.BODY
        if (!okHttpClient.interceptors().contains(logging)) okHttpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }


}