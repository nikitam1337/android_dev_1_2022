package com.example.hw2.customView

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.hw2.R
import com.example.hw2.databinding.CustomViewBinding

class CustomView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet) {
    private val binding: CustomViewBinding
    init {
        val inflatedView = inflate(context, R.layout.custom_view, this)
        binding = CustomViewBinding.bind(inflatedView)
    }

    fun setMessageTopText(text: String) {
        binding.topText.text = text
    }

    fun setMessageBottomText(text: String) {
        binding.bottomText.text = text
    }
}
