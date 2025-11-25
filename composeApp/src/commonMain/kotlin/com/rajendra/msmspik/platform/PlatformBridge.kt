package com.rajendra.msmspik.platform

import androidx.compose.runtime.Composable


expect fun getPlatform(): AppConfig
@Composable
expect fun getDrawable(): AppDrawable
@Composable
expect fun getThemColor(): ThemeColor
