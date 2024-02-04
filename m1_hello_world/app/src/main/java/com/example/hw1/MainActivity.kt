package com.example.hw1

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewMessage = findViewById<TextView>(R.id.textViewMessage)
        val textViewNumber = findViewById<TextView>(R.id.textViewCounter)
        val buttonMinus = findViewById<ImageButton>(R.id.buttonMinus)
        val buttonPlus = findViewById<ImageButton>(R.id.buttonPlus)
        val buttonReset = findViewById<Button>(R.id.buttonReset)

        buttonMinus.setOnClickListener {
            if (counter > 0) {
                counter--
                textViewNumber.text = counter.toString()
                updateUI(textViewMessage, buttonMinus, buttonPlus, buttonReset)
            }
        }

        buttonPlus.setOnClickListener {
            if (counter < 50) {
                counter++
                textViewNumber.text = counter.toString()
                updateUI(textViewMessage, buttonMinus, buttonPlus, buttonReset)
            }
        }

        buttonReset.setOnClickListener {
            counter = 0
            textViewNumber.text = counter.toString()
            updateUI(textViewMessage, buttonMinus, buttonPlus, buttonReset)
        }
    }

    private fun updateUI(textViewMessage: TextView, buttonMinus: ImageButton, buttonPlus: ImageButton, buttonReset: Button) {
        val remainingSeats = 50 - counter
        when {
            counter == 0 -> {
                textViewMessage.text = "Все места свободны"
                textViewMessage.setTextColor(Color.GREEN)
                buttonMinus.isEnabled = false
                buttonPlus.isEnabled = true
                buttonReset.visibility = Button.GONE
            }
            counter in 1..49 -> {
                textViewMessage.text = "Осталось мест: $remainingSeats"
                textViewMessage.setTextColor(Color.BLUE)
                buttonMinus.isEnabled = true
                buttonPlus.isEnabled = true
                buttonReset.visibility = Button.GONE
            }
            counter >= 50 -> {
                textViewMessage.text = "Пассажиров слишком много!"
                textViewMessage.setTextColor(Color.RED)
                buttonMinus.isEnabled = true
                buttonPlus.isEnabled = false
                buttonReset.visibility = Button.VISIBLE
            }
        }
    }
}