package com.rajendra.msmspik

// In your 'iosMain' source set (e.g., ios/src/main/kotlin/UIImageConverter.kt)

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorSpace
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo
import platform.CoreFoundation.CFDataGetBytePtr
import platform.CoreFoundation.CFDataGetLength
import platform.CoreFoundation.CFRelease
import platform.CoreGraphics.CGDataProviderCopyData
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGImageGetAlphaInfo
import platform.CoreGraphics.CGImageGetBytesPerRow
import platform.CoreGraphics.CGImageGetDataProvider
import platform.CoreGraphics.CGImageGetHeight
import platform.CoreGraphics.CGImageGetWidth
import platform.UIKit.UIImage

@OptIn(ExperimentalForeignApi::class)
fun UIImage.toComposeImageBitmap(): ImageBitmap {
    val skiaImage = this.toSkiaImage() ?: return ImageBitmap(1, 1)
    return skiaImage.toComposeImageBitmap()
}

@OptIn(ExperimentalForeignApi::class)
 fun UIImage.toSkiaImage(): Image? {
    // 1. Get the Core Graphics image reference
    val imageRef = this.CGImage ?: return null
    val width = CGImageGetWidth(imageRef).toInt()
    val height = CGImageGetHeight(imageRef).toInt()
    val bytesPerRow = CGImageGetBytesPerRow(imageRef)

    // 2. Extract pixel data using a CGDataProvider
    val data = CGDataProviderCopyData(CGImageGetDataProvider(imageRef))
    val bytePointer = CFDataGetBytePtr(data)
    val length = CFDataGetLength(data).toInt()

    // 3. Determine Alpha Type for Skia
    val alphaType = when (CGImageGetAlphaInfo(imageRef)) {
        CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst, CGImageAlphaInfo.kCGImageAlphaPremultipliedLast -> ColorAlphaType.PREMUL
        CGImageAlphaInfo.kCGImageAlphaFirst, CGImageAlphaInfo.kCGImageAlphaLast -> ColorAlphaType.UNPREMUL
        else -> ColorAlphaType.OPAQUE // No Alpha
    }

    // 4. Copy data into a Kotlin ByteArray
    val byteArray = bytePointer!!.readBytes(length)
    // 5. Clean up C pointers
    CFRelease(data)
    // NOTE: In some environments, CGImageRelease(imageRef) can cause a crash if the imageRef
    // is managed by the original UIImage. It's often safer to omit this line.

    // 6. Create a Skia Image from the raw bytes
    return Image.makeRaster(
        imageInfo = ImageInfo(
            width = width,
            height = height,
            colorType = ColorType.RGBA_8888, // Standard color format
            alphaType = alphaType,
            colorSpace = ColorSpace.sRGB
        ),
        bytes = byteArray,
        rowBytes = bytesPerRow.toInt(),
    )
}