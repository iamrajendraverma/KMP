package com.rajendra.msmspik.platform

import android.os.Build
import com.rajendra.msmspik.AndroidApp


actual fun getPlatform(): AppConfig {
    return AndroidApp.get().androidAppConfig
}

