package com.example.know_everything

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import com.example.know_everything.databinding.ActivityMainBinding
import com.example.know_everything.databinding.WelcomeScreenBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = WelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}