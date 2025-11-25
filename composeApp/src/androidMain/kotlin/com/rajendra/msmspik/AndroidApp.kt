package com.rajendra.msmspik

import android.app.Application
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.rajendra.msmspik.platform.AndroidAppConfig
import com.rajendra.msmspik.platform.AppData
import com.rajendra.msmspik.platform.AppDrawable
import com.rajendra.msmspik.platform.AppLocalization

class AndroidApp : Application() {
    companion object {
        lateinit var instance: AndroidApp
            private set

        fun get(): AndroidApp {
            return instance
        }
    }

    lateinit var androidAppConfig: AndroidAppConfig

    override fun onCreate() {
        super.onCreate()
        instance = this
        val data = AppData(
            appId = BuildConfig.APPLICATION_ID,
            versionName = BuildConfig.VERSION_NAME,
            version = BuildConfig.VERSION_CODE.toString()
        )
        val drawable = getDrawable(R.drawable.ic_launcher_background)
        val bitmap = (drawable as? BitmapDrawable)?.bitmap
        val appDrawable =
            bitmap?.asImageBitmap()?.let { AppDrawable(appLogo = BitmapPainter(image = it)) }
        val appLocalization = AppLocalization(displayName = getString(R.string.display_name))
        androidAppConfig = AndroidAppConfig(data, appLocalization, appDrawable)

    }
}

