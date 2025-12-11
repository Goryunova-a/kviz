package com.example.simplequiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.simplequiz.model.Question
import androidx.activity.addCallback
import androidx.lifecycle.Observer

class QuizActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var questionText: TextView
    private lateinit var progressText: TextView
    private lateinit var optionButtons: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        questionText = findViewById(R.id.textViewQuestion)
        progressText = findViewById(R.id.textViewProgress)
        optionButtons = arrayOf(
            findViewById(R.id.buttonOption1),
            findViewById(R.id.buttonOption2),
            findViewById(R.id.buttonOption3),
            findViewById(R.id.buttonOption4)
        )

        optionButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.answerSelected(index)
                // LiveData сам обновит UI через observe
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(this@QuizActivity, "Завершите викторину", Toast.LENGTH_SHORT).show()
        }

        viewModel.currentQuestionIndex.observe(this, Observer { updateUI() })
        viewModel.isFinished.observe(this, Observer { isFinished ->
            if (isFinished) {
                val score = viewModel.score.value ?: 0
                val total = viewModel.questions.size
                val userName = viewModel.userName.value ?: "Гость"

                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("score", score)
                    putExtra("total", total)
                    putExtra("user_name", userName)
                }
                startActivity(intent)
                finish()
            }
        })

        updateUI()
    }

    private fun updateUI() {
        val question = viewModel.getCurrentQuestion()
        val currentIndex = viewModel.currentQuestionIndex.value ?: 0
        val total = viewModel.questions.size

        if (question != null) {
            questionText.text = question.text
            progressText.text = "${currentIndex + 1}/$total"
            question.options.forEachIndexed { i, option ->
                if (i < optionButtons.size) {
                    optionButtons[i].text = option
                    optionButtons[i].visibility = View.VISIBLE
                }
            }
            for (i in question.options.size until optionButtons.size) {
                optionButtons[i].visibility = View.GONE
            }
        } else {
            Toast.makeText(this, "Ошибка: вопрос не загружен", Toast.LENGTH_SHORT).show()
        }
    }
}