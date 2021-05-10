package com.github.mrbean355.parcelizebug

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.parcel.Parcelize

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyParcelableModel(null)
            .writeToParcel(Parcel.obtain(), 0)
    }
}

@Parcelize
data class MyParcelableModel(
    val items: Array<Int>?
) : Parcelable