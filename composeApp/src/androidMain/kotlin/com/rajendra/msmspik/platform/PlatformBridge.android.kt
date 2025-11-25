package com.rajendra.msmspik.platform

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.rajendra.msmspik.AndroidApp
import com.rajendra.msmspik.R
import msmspik.composeapp.generated.resources.Res


actual fun getPlatform(): AppConfig {
    return AndroidApp.get().androidAppConfig
}

@Composable
actual fun getDrawable(): AppDrawable {
    return AppDrawable(appLogo = painterResource(R.drawable.tenant_logo))

}
@Composable
actual fun getThemColor(): ThemeColor {
    val context  = LocalContext.current
    val primary  = colorResource(R.color.primary)
    val secondary  = colorResource(R.color.secondary)
    val accent  = colorResource(R.color.accent)
    return ThemeColor(primary = primary,secondary=secondary,accent=accent)
}