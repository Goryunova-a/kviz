package com.example.simplequiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 1)
        val userName = intent.getStringExtra("user_name") ?: "Гость"

        findViewById<TextView>(R.id.textViewName).text = "Игрок: $userName"
        findViewById<TextView>(R.id.textViewScore).text = "Результат: $score из $total"

        findViewById<Button>(R.id.buttonRestart).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}