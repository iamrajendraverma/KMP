package com.rajendra.msmspik.platform

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
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
import platform.UIKit.UIImage

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

    override val appLocalization: AppLocalization = AppLocalization(displayName = bundle.localizedStringForKey("display_name", "N/A", null))
    override val appDrawable: AppDrawable = AppDrawable(appLogo = getDrawableResourceName("appLogo"))

}

fun getDictionary(): NSDictionary {
    return NSBundle.mainBundle.infoDictionary as NSDictionary
}

@OptIn(ExperimentalForeignApi::class)
fun getDrawableResourceName(name: String): Painter {
    // 1. Load UIImage from the Assets.xcassets catalog.
    val uiImage = UIImage.imageNamed(name)

    return BitmapPainter(uiImage?.toImageBitmap())
}


