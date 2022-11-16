package com.example.ejerciciounoapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class InfoAlumn (
    var name:String = "",
    var birthday:String = "",
    var noCount:String = "",
    var email:String = ""
) : Parcelable

