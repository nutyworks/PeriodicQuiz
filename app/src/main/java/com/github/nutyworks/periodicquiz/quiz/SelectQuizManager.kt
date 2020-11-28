package com.github.nutyworks.periodicquiz.quiz

import android.util.Log

class SelectQuizManager(private val activity: SelectQuizActivity) {
    val results = ArrayList<Boolean>()
    private var recentQuiz: QuizInfo? = null

    fun makeQuiz(questions: List<Any?>, answers: List<Any?>,
                 id: Int = questions.indices.random(), maxAnswers: Int = 5): QuizInfo {
        val question = questions[id]
        val (quizAnswers, correctAnswerIndex) = answers
            .filterIndexed { index, value -> id != index && value != answers[id] }
            .distinct().shuffled().let {
                val subSize = if (it.size >= maxAnswers) maxAnswers else it.size
                val correctAnswerIndex = (0 until subSize).random()

                it.subList(0, correctAnswerIndex)
                    .plus(answers[id])
                    .plus(it.subList(correctAnswerIndex, subSize - 1)) to correctAnswerIndex
            }

        recentQuiz = QuizInfo(
            question.toString(),
            quizAnswers.map { e -> e.toString() }.toList(),
            correctAnswerIndex
        )
        return recentQuiz!!
    }

    fun onSelectAnswer(selectedAnswer: Int) {
        results.add(recentQuiz?.correctAnswer == selectedAnswer)
        activity.onAnswerSelected()
    }
}