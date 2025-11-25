package com.rajendra.msmspik

// In your 'iosMain' source set (e.g., ios/src/main/kotlin/UIImageConverter.kt)

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import platform.UIKit.UIImage

/**
 * Converts a UIImage to a Compose ImageBitmap.
 */
fun UIImage.toImageBitmap(): ImageBitmap? {
    // 1. Get the underlying CGImage
    val cgImage = this.CGImage ?: return null

    // 2. Extract essential properties
    val width = CGImageGetWidth(cgImage).toInt()
    val height = CGImageGetHeight(cgImage).toInt()
    val bytesPerRow = CGImageGetBytesPerRow(cgImage)

    // 3. Copy image data provider
    val data = CGDataProviderCopyData(CGImageGetDataProvider(cgImage)) ?: return null
    val bytePointer = CFDataGetBytePtr(data)
    val length = CFDataGetLength(data)

    // 4. Determine Skia Alpha Type
    val alphaType = when (CGImageGetAlphaInfo(cgImage)) {
        CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst,
        CGImageAlphaInfo.kCGImageAlphaPremultipliedLast -> ColorAlphaType.PREMUL
        CGImageAlphaInfo.kCGImageAlphaFirst,
        CGImageAlphaInfo.kCGImageAlphaLast -> ColorAlphaType.UNPREMUL
        else -> ColorAlphaType.OPAQUE
    }

    // 5. Copy raw CFData to a Kotlin ByteArray
    val byteArray = ByteArray(length.toInt()) { index -> bytePointer!![index].toByte() }
    CFRelease(data) // Release the Core Foundation object

    // 6. Create Skia ImageInfo
    val imageInfo = ImageInfo.make(
        width = width,
        height = height,
        colorType = ColorType.RGBA_8888,
        alphaType = alphaType,
        colorSpace = ColorSpace.sRGB
    )

    // 7. Create Skia Image and convert to Compose ImageBitmap
    return Image.makeRaster(
        imageInfo = imageInfo,
        bytes = byteArray,
        rowBytes = bytesPerRow.toInt()
    )?.toComposeImageBitmap()
}