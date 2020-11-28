package com.github.nutyworks.periodicquiz.quiz

import java.io.Serializable

data class QuizInfo(
    val question: String,
    val answerList: List<String>,
    val correctAnswer: Int
) : Serializable