package br.com.matheus.userprojectbrowser.base.extension

import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import android.support.v7.app.ActionBar
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.matheus.userprojectbrowser.base.picasso.transformation.CircleTransform
import com.squareup.picasso.Picasso

fun View.addStatusBarPadding() {
    setPadding(paddingLeft,
            paddingTop + context.statusBarHeight(), paddingRight,
            paddingBottom)
}

fun View.addStatusBarMargin() {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.topMargin += context.statusBarHeight()
}

fun ActionBar?.enableBack() {
    if (this == null) return
    setDisplayHomeAsUpEnabled(true)
    setDisplayShowHomeEnabled(true)
}

fun ImageView.loadUrl(url: String, @DrawableRes placeholder: Int = 0, @DrawableRes error: Int = placeholder) {
    if (url.isEmpty()) return
    Picasso.with(context).load(url).apply {
        transform(CircleTransform())
        if (placeholder > 0) placeholder(placeholder)
        if (error > 0) error(error)
    }.into(this)
}

@Suppress("DEPRECATION")
fun TextView.setCustomTextAppearance(@StyleRes styleRes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) setTextAppearance(styleRes)
    else setTextAppearance(context, styleRes)
}

inline fun TypedArray.obtain(func: TypedArray.() -> Unit) {
    func()
    recycle()
}