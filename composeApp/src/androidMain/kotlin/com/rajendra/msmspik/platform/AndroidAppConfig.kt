package com.rajendra.msmspik.platform

class AndroidAppConfig(override val appData: AppData,
                       override val appLocalization: AppLocalization,
                       override val appDrawable: AppDrawable?
) : AppConfig {
}