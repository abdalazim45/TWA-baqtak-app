package com.baqtak.app

import android.content.Intent
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.androidbrowserhelper.trusted.LauncherActivity

class BaqtakLauncherActivity : LauncherActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemBars()
        super.onCreate(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            setIntent(intent)
        }
    }

    private fun hideSystemBars() {
        val controller = WindowCompat.getInsetsController(
            window,
            window.decorView
        )

        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        controller.hide(WindowInsetsCompat.Type.systemBars())
    }
}
