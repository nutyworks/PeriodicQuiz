package com.github.nutyworks.periodicquiz.quiz

import java.io.Serializable

data class QuizData(
    val questionType: QuizType,
    val question: String,
    val answers: List<String>,
    val correctAnswer: Any,
    var selectedAnswer: Any?,
    var answered: Boolean = false
) : Serializable {
    fun isCorrect(): Boolean = correctAnswer == selectedAnswer
}

enum class QuizType {
    MULTIPLE_CHOICE,
    WRITTEN,
    OX;
}
