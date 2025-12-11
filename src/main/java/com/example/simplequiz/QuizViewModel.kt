package com.example.simplequiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplequiz.model.Question

class QuizViewModel : ViewModel() {

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _isFinished = MutableLiveData(false)
    val isFinished: LiveData<Boolean> = _isFinished

    private val _userName = MutableLiveData("Гость")
    val userName: LiveData<String> = _userName

    val questions = listOf(
        Question("Столица Франции?", listOf("Париж", "Лондон", "Берлин", "Мадрид"), 0),
        Question("2 + 2 = ?", listOf("3", "4", "5", "6"), 1),
        Question("Какой цвет у неба?", listOf("Зелёный", "Красный", "Синий", "Чёрный"), 2),
        Question("Сколько планет в Солнечной системе?", listOf("7", "8", "9", "10"), 1)
    )

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun answerSelected(selectedIndex: Int) {
        val currentIndex = _currentQuestionIndex.value ?: 0
        if (currentIndex < questions.size) {
            val currentQuestion = questions[currentIndex]
            val isCorrect = (selectedIndex == currentQuestion.correctIndex)

            // 🔴 ЛОГИРУЕМ ВСЁ
            println("🔹 Вопрос: ${currentQuestion.text}")
            println("🔹 Выбран индекс: $selectedIndex → '${currentQuestion.options[selectedIndex]}'")
            println("🔹 Правильный индекс: ${currentQuestion.correctIndex} → '${currentQuestion.options[currentQuestion.correctIndex]}'")
            println("🔹 Совпадает? $isCorrect")

            if (isCorrect) {
                val oldScore = _score.value ?: 0
                val newScore = oldScore + 1
                _score.value = newScore
                println("✅ Счёт обновлён: $oldScore → $newScore")
            } else {
                println("❌ Ответ неверный — счёт: ${_score.value ?: 0}")
            }

            if (currentIndex + 1 < questions.size) {
                _currentQuestionIndex.value = currentIndex + 1
                println("➡️ Переход к вопросу ${currentIndex + 2}")
            } else {
                _isFinished.value = true
                println("🏁 Викторина завершена. Итоговый счёт: ${_score.value ?: 0}")
            }
        }
    }

    fun resetQuiz() {
        _currentQuestionIndex.value = 0
        _score.value = 0
        _isFinished.value = false
    }

    fun getCurrentQuestion(): Question? {
        val index = _currentQuestionIndex.value ?: 0
        return questions.getOrNull(index)
    }
}