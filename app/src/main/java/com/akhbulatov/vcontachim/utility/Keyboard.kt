package com.akhbulatov.vcontachim.utility

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


class Keyboard : Fragment() {

    fun hideKeyBoard() {
        activity?.hideKeyBoard(view = requireView())
    }

    private fun Context.hideKeyBoard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}