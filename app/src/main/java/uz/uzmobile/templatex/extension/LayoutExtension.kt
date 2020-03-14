package uz.uzmobile.templatex.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)


