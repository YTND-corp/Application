package uz.mod.templatex.utils.extension

import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.inflate(layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)


