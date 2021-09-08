package com.s2.made.mycatalog15.utils

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class Util {
    companion object {
        fun showMessage(view: View?, message: String) {
            if (view != null) {
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Close") {
                    Toast.makeText(view.context, "Closed", Toast.LENGTH_SHORT).show()
                }.show()
            }
        }
    }
}