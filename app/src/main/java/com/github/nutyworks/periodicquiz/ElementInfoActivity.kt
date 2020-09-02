package com.github.nutyworks.periodicquiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_element_information.*

class ElementInfoActivity : AppCompatActivity() {

    companion object {
        const val ELEMENT_INFO_NUMBER = "com.github.butyworks.ELEMENT_INFO_NUMBER"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_element_information)

        val elementNumber = intent.getIntExtra(ELEMENT_INFO_NUMBER, -1)

        if (elementNumber == -1) {
            return
        }

        val element = MainActivity.elements[elementNumber]

        element_info_symbol.text = element.symbol
        element_info_name.text = element.name
        element_info_number.text = element.number.toString()
        Glide.with(this).load(element.spectral_img/*.replace("https://", "http://")*/).into(element_info_spectral_img)
        Log.d("ELEMENT SPECTRAL IMG", element.spectral_img + " ")
    }
}