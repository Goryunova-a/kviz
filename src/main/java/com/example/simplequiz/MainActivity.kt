package com.example.simplequiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        val editText = findViewById<EditText>(R.id.editTextName)
        val buttonStart = findViewById<Button>(R.id.buttonStart)

        editText.setText(viewModel.userName.value)

        buttonStart.setOnClickListener {
            val name = editText.text.toString().trim()
            viewModel.setUserName(name)

            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}