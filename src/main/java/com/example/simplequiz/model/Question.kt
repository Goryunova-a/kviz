package com.example.simplequiz.model

data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)