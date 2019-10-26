package com.github.khaledakassem.photo_weather.common.extensions

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.common.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import java.nio.ByteBuffer



fun Fragment.getThisColor(@ColorRes id: Int) = activity?.getThisColor(id)



fun Fragment.errorMsg(msg: String, duration: Int = Constants.SNAK_BAR_DURATION) {
    val snackbar = Snackbar.make(view!!, msg, Snackbar.LENGTH_LONG)
    snackbar.view.setBackgroundColor(getThisColor(android.R.color.holo_red_dark)!!)
    val textView =snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(getThisColor(android.R.color.white)!!)
    snackbar.show()
}

fun Fragment.errorMsg(@StringRes msgId: Int, duration: Int = Constants.SNAK_BAR_DURATION) {
    errorMsg(getString(msgId), duration)
}

fun Fragment.warningMsg(msg: String, duration: Int = Constants.SNAK_BAR_DURATION) {
    val snackbar = Snackbar.make(view!!, msg, Snackbar.LENGTH_LONG)
    snackbar.view.setBackgroundColor(getThisColor(android.R.color.holo_orange_light)!!)
    val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(getThisColor(android.R.color.black)!!)
    snackbar.show()
}

fun Fragment.warningMsg(@StringRes msgId: Int, duration: Int = Constants.SNAK_BAR_DURATION) {
    warningMsg(getString(msgId), duration)
}

fun Fragment.confirmMsg(msg: String) {
    val snackbar = Snackbar.make(view!!, msg, Snackbar.LENGTH_SHORT)
    snackbar.view.setBackgroundColor(getThisColor(android.R.color.holo_green_dark)!!)
    val textView = snackbar.view.findViewById(R.id.snackbar_text) as TextView
    textView.setTextColor(getThisColor(android.R.color.black)!!)
    val params = snackbar.view.layoutParams as ViewGroup.MarginLayoutParams
//    params.setMargins(
//            10, 0,
//            10,
//            activity!!.findViewById<BottomNavigationView>(R.id.navigationView).height + 20)

    snackbar.view.layoutParams = params
    snackbar.view.setBackgroundResource(R.drawable.snackbar_radius)
    snackbar.show()
}


fun Fragment.confirmMsg(@StringRes msgId: Int, duration: Int = Constants.SNAK_BAR_DURATION) {
    warningMsg(getString(msgId), duration)
}
