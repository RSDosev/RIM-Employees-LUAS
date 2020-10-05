package com.rsdosev.rimemployeesluas.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible

fun View.show() {
    isVisible = true
}
fun View.hide() {
    isVisible = false
}

fun ViewGroup.hideAllChildrenExcept(child:View){
    children.forEach {
        if (it.id != child.id) {
            it.hide()
        }
    }
}

fun ViewGroup.showAllChildrenExcept(child:View){
    children.forEach {
        if (it.id != child.id) {
            it.show()
        }
    }
}