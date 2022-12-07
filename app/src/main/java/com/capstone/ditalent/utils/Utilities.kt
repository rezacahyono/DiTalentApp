package com.capstone.ditalent.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.capstone.ditalent.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider

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
    private fun Context.resolveThemeAttr(@AttrRes attrRes: Int): TypedValue {
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

    fun String.getInitialName(): String {
        if (this.isEmpty()) return ""
        val stringBuilder = StringBuilder()
        val split = this.split(" ")
        for ((index, s) in split.withIndex()) {
            stringBuilder.append(s.first())
            if (index >= 1) {
                break
            }
        }
        return stringBuilder.toString()
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    fun Context.dpToPx(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

    fun randomColor(context: Context): Int {
        val color = when ((1..5).random()) {
            1 -> R.color.orange_light
            2 -> R.color.blue
            3 -> R.color.mint_green
            4 -> R.color.red
            else -> R.color.dark_green
        }
        return ContextCompat.getColor(context, color)
    }

    fun handlingException(exception: Throwable): Int {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> R.string.text_message_error_auth_something_wrong
            is FirebaseAuthInvalidCredentialsException -> R.string.text_message_error_auth_something_wrong
            is FirebaseNetworkException -> R.string.text_message_error_internet_connection
            else -> R.string.text_message_error_something
        }
    }
}