package com.github.nutyworks.periodicquiz

import java.io.Serializable

data class QuizInfo(
    val question: String,
    val answerList: List<String>,
    val correctAnswer: Int
) : Serializable