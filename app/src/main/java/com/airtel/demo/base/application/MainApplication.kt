package com.airtel.demo.base.application

import android.app.Application
import com.airtel.demo.base.di.AppModule
import com.airtel.demo.base.di.BaseAppComponent
import com.airtel.demo.base.di.DaggerBaseAppComponent

public class MainApplication : Application() {

    private var baseAppComponent: BaseAppComponent? = null


    override fun onCreate() {
        super.onCreate()

    }

    fun getBaseAppComponent(): BaseAppComponent? {
        if (baseAppComponent == null) {
            val daggerBuilder: DaggerBaseAppComponent.Builder = DaggerBaseAppComponent.builder()
                .appModule(AppModule(this))
            baseAppComponent = daggerBuilder.build()
        }
        return baseAppComponent
    }

}