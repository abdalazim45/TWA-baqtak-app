package com.baqtak.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // IntroActivity is no longer used.
        // LauncherActivity handles the TWA directly.
        finish()
    }
}
