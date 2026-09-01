package com.baqtak.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ============================================================
        // شاشة البداية:
        // خلفية بيضاء + شعار باقتك فقط في المنتصف
        // ============================================================

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setStatusBarColor(getColor(R.color.white))
        window.setNavigationBarColor(getColor(R.color.white))

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(getColor(R.color.white))
        }

        val logo = ImageView(this).apply {
            setImageResource(R.drawable.app_logo_onboarding)

            // يمنع تشوه الشعار
            scaleType = ImageView.ScaleType.CENTER_INSIDE

            // يمنع تمدد الصورة
            adjustViewBounds = true
        }

        val logoSize =
            (160 * resources.displayMetrics.density).toInt()

        root.addView(
            logo,
            LinearLayout.LayoutParams(
                logoSize,
                logoSize
            )
        )

        setContentView(root)

        // ============================================================
        // تشغيل TWA مباشرة بعد ظهور الشعار
        // ============================================================

        window.decorView.post {
            launchTwa()
        }
    }

    private fun launchTwa() {

        val intent = Intent(
            this,
            com.google.androidbrowserhelper.trusted.LauncherActivity::class.java
        ).apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("https://baqtak.com/")
        }

        startActivity(intent)

        // إغلاق IntroActivity بعد تسليم التشغيل للـTWA
        finish()
    }
}
