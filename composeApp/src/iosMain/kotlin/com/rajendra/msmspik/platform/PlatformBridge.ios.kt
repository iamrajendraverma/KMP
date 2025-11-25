package com.rajendra.msmspik.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.rajendra.msmspik.toComposeColor
import com.rajendra.msmspik.toComposeImageBitmap
import com.rajendra.msmspik.toSkiaImage
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import org.jetbrains.skia.Image
import platform.CoreGraphics.CGImageGetDataProvider
import platform.CoreGraphics.CGImageGetHeight
import platform.CoreGraphics.CGImageGetWidth
import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import org.jetbrains.skia.*
import platform.CoreFoundation.CFDataGetBytePtr
import platform.CoreFoundation.CFDataGetLength
import platform.CoreFoundation.CFRelease
import platform.CoreGraphics.*
import platform.Foundation.*
import platform.UIKit.UIColor
import platform.UIKit.UIImage
import platform.UIKit.colorNamed
import platform.UIKit.systemGray2Color
import platform.UIKit.systemRedColor

actual fun getPlatform(): AppConfig {
    return IOSAppConfig()
}

class IOSAppConfig() : AppConfig {

    val infoDic = getDictionary()

    private fun getString(key: String): String {
        return infoDic.objectForKey(key) as? String ?: "N/A: $key"
    }

    override val appData: AppData = AppData(
        appId = getString("CFBundleIdentifier"),
        versionName = getString("CFBundleShortVersionString"),
        version = getString("CFBundleVersion")

    )
    val bundle = NSBundle.mainBundle

    override val appLocalization: AppLocalization =
        AppLocalization(displayName = bundle.localizedStringForKey("display_name", "N/A", null))
}

fun getDictionary(): NSDictionary {
    return NSBundle.mainBundle.infoDictionary as NSDictionary
}


@Composable
actual fun getDrawable(): AppDrawable {
    val bundle = NSBundle.mainBundle
    val imageUi = UIImage.imageNamed("tenant_logo")
    val appLogo = imageUi?.toComposeImageBitmap()
    return AppDrawable(appLogo = BitmapPainter(appLogo!!))
}

@Composable
actual fun getThemColor(): ThemeColor {
   val primary = UIColor.colorNamed("primary")
   val secondary = UIColor.colorNamed("secondary")
   val accent = UIColor.colorNamed("accent")
    return ThemeColor(primary = primary?.toComposeColor(),secondary = secondary?.toComposeColor(),accent = accent?.toComposeColor())
}