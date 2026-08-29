/*
 * =====================================================================
 * IntroActivity.kt
 * =====================================================================
 * PURPOSE:
 *   Launcher activity for Baqtak.
 *
 *   First launch:
 *     Android Splash → Onboarding → Get Started → TWA
 *
 *   Later launches:
 *     Android Splash → TWA
 *
 *   The Android Splash Screen API is used as the first visual screen.
 *   The TWA is then launched using TwaLauncher.
 * =====================================================================
 */

package com.baqtak.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.baqtak.app.databinding.ActivityIntroBinding

import com.google.android.play.core.install.model.AppUpdateType
import com.google.androidbrowserhelper.trusted.TwaLauncher

// ─── SharedPreferences Constants ─────────────────────────────────────

const val PREF_NAME = "app_prefs"
const val KEY_STARTED = "started"

/**
 * IntroActivity
 *
 * First installation:
 *   Shows the onboarding screen.
 *
 * Subsequent launches:
 *   Skips onboarding and launches the TWA directly.
 */
class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    private lateinit var appUpdateManagerUtil: AppUpdateManagerUtil

    override fun onCreate(savedInstanceState: Bundle?) {

        /*
         * =============================================================
         * STEP 1 — Install Android Splash Screen
         * =============================================================
         *
         * IMPORTANT:
         * Do NOT use:
         *
         * installSplashScreen().setKeepOnScreenCondition { false }
         *
         * because that explicitly tells Android to dismiss the
         * splash immediately.
         *
         * Android will now manage the Splash Screen normally.
         */
        installSplashScreen()

        super.onCreate(savedInstanceState)

        /*
         * =============================================================
         * STEP 2 — Inflate Intro Layout
         * =============================================================
         */
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
         * =============================================================
         * STEP 3 — Check Onboarding Status
         * =============================================================
         */
        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val hasStarted = prefs.getBoolean(KEY_STARTED, false)

        /*
         * =============================================================
         * STEP 4 — Returning User
         * =============================================================
         *
         * If onboarding was already completed:
         *
         * Splash
         *   ↓
         * TWA
         *
         * The onboarding UI is never made visible.
         */
        if (hasStarted) {

            // Keep the onboarding layout hidden.
            binding.parent.visibility = View.GONE

            // Launch the TWA immediately.
            startMain()

            return
        }

        /*
         * =============================================================
         * STEP 5 — First Launch
         * =============================================================
         *
         * Show the existing onboarding screen.
         */
        binding.parent.visibility = View.VISIBLE

        /*
         * =============================================================
         * STEP 6 — Read TWA Manifest
         * =============================================================
         */
        val manifest = readManifestData(this)

        /*
         * =============================================================
         * STEP 7 — Set App Name & Description
         * =============================================================
         */
        binding.itemTitle.text = manifest.name
        binding.subtitle.text = manifest.description

        /*
         * =============================================================
         * STEP 8 — Get Started
         * =============================================================
         */
        binding.getStartedButton.setOnClickListener {

            // Save onboarding completion.
            prefs.edit {
                putBoolean(KEY_STARTED, true)
            }

            // Hide onboarding before starting TWA.
            binding.parent.visibility = View.GONE

            // Launch website through TWA.
            startMain()
        }

        /*
         * =============================================================
         * STEP 9 — Google Play In-App Update
         * =============================================================
         */
        appUpdateManagerUtil =
            AppUpdateManagerUtil(
                this,
                binding,
                AppUpdateType.IMMEDIATE
            ).apply {
                checkForUpdate()
            }
    }

    /**
     * ================================================================
     * Launch TWA
     * ================================================================
     *
     * Reads start_url from twa-manifest.json and launches the website
     * through Google's Trusted Web Activity launcher.
     */
    private fun startMain() {

        val manifest = readManifestData(this)

        TwaLauncher(this).launch(
            manifest.startUrl.toUri()
        )
    }
}
