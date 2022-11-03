package com.capstone.ditalent.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Patterns

object Utilities {

    @Suppress("DEPRECATION")
    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(source)
        }
    }

    fun String.isNotValidEmail() = this.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(this).matches()
}