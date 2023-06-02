package com.akhbulatov.vcontachim.utility

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.akhbulatov.vcontachim.VcontachimApplication

object Keyboard {

    fun hideKeyBoard(view: View) {
        VcontachimApplication.context.hideKeyBoard(view = view)
    }

    private fun Context.hideKeyBoard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}