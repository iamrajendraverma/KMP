package com.rajendra.msmspik

import androidx.compose.ui.graphics.Color
import platform.UIKit.UIColor
import platform.CoreGraphics.CGFloat
import kotlinx.cinterop.*

// :shared/src/iosMain/kotlin/UIColorExtensions.kt

import platform.CoreGraphics.CGFloatVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr

@OptIn(ExperimentalForeignApi::class)
fun UIColor.toComposeColor(): Color {
    // 👇 This is the essential fix for the 'alloc' error
    return memScoped {
        // 1. Allocate space for the pointers on the stack
        val redPtr = alloc<CGFloatVar>()
        val greenPtr = alloc<CGFloatVar>()
        val bluePtr = alloc<CGFloatVar>()
        val alphaPtr = alloc<CGFloatVar>()

        // 2. Pass the pointers (.ptr) to the native function
        this@toComposeColor.getRed(
            red = redPtr.ptr,
            green = greenPtr.ptr,
            blue = bluePtr.ptr,
            alpha = alphaPtr.ptr
        )

        // 3. Return the Compose Color
        Color(
            red = redPtr.value.toFloat(),
            green = greenPtr.value.toFloat(),
            blue = bluePtr.value.toFloat(),
            alpha = alphaPtr.value.toFloat()
        )
    }
}