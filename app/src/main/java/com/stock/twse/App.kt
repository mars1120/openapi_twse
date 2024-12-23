package com.stock.twse

import android.app.Application
import android.content.Context

import com.stock.twse.db.AppDatabase


class App : Application() {
    companion object {

        lateinit var appContext: Context
        lateinit var db: AppDatabase

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        db = AppDatabase.getInstance(appContext)
    }


}
