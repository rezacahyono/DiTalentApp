package com.capstone.ditalent.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object Utilities {

    /**
     * @param source is string html
     */
    @Suppress("DEPRECATION")
    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(source)
        }
    }

    /**
     * Function check string matching format email
     */
    fun String.isNotValidEmail() =
        this.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(this).matches()

    /**
     * @param context
     * @param view parent
     * Hide keyboard
     */
    fun hideSoftKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * @param context
     * @param view parent
     * Show keyboard
     */
    fun showSoftKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, 0)
    }

    /**
     * @param colorAttr color
     */
    @ColorInt
    fun Context.resolveColorAttr(@AttrRes colorAttr: Int): Int {
        val resolvedAttr = resolveThemeAttr(colorAttr)
        val colorRes =
            if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
        return ContextCompat.getColor(this, colorRes)
    }

    /**
     * @param attrRes theme
     */
    fun Context.resolveThemeAttr(@AttrRes attrRes: Int): TypedValue {
        val typedValue = TypedValue()
        theme.resolveAttribute(attrRes, typedValue, true)
        return typedValue
    }

    /**
     * @param context
     * @param content
     * @param view parent root
     * @param attachView view binding
     * @param action lambda function
     * @param actionText
     */
    fun showSnackBar(
        context: Context,
        content: String,
        view: View,
        attachView: View? = null,
        action: (() -> Unit)? = null,
        actionText: String? = null,
    ) {
        val snackBar = Snackbar.make(context, view, content, 5000)
        snackBar.isGestureInsetBottomIgnored = true
        if (attachView != null)
            snackBar.anchorView = attachView
        if (action != null) {
            snackBar.setActionTextColor(context.resolveColorAttr(android.R.attr.colorAccent))
            snackBar.setAction(actionText) {
                action()
            }
        }
        snackBar.show()
    }

    fun String.clearNoPhone(): String {
        val region = this.first() == '+'
        val zero = this.first() == '0'
        return when {
            region -> this
            zero -> this.replace("0", "+62")
            else -> "+62".plus(this)
        }
    }
}