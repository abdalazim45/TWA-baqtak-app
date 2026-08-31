package com.baqtak.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.google.androidbrowserhelper.trusted.TwaLauncher

/**
 * Launcher Activity for Baqtak.
 *
 * Flow:
 * Android Splash
 *      ↓
 * Launch TWA
 *      ↓
 * baqtak.com
 *      ↓
 * Full Screen
 *
 * No onboarding screen is used.
 */
class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // Install the Android Splash Screen before super.onCreate().
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Launch the TWA immediately.
        startMain()
    }

    /**
     * Launches Baqtak website using Trusted Web Activity.
     */
    private fun startMain() {

        val manifest = readManifestData(this)

        TwaLauncher(this).launch(
            manifest.startUrl.toUri()
        )
    }
}
