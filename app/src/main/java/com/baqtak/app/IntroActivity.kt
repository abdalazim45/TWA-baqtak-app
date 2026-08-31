package com.baqtak.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.net.toUri
import com.google.androidbrowserhelper.trusted.TwaLauncher

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // Android system splash.
        // It remains visible only during the very first Android startup.
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Start the TWA immediately.
        // The TWA Splash configuration in AndroidManifest.xml
        // handles the transition while Chrome/TWA is initializing.
        startTwa()
    }

    private fun startTwa() {
        val manifest = readManifestData(this)

        TwaLauncher(this).launch(
            manifest.startUrl.toUri()
        )
    }
}
