package com.github.nutyworks.periodicquiz.quiz

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class QuizManager(private val activity: QuizActivity, allowedTypes: EnumSet<QuizType>) {

    val info = Info(allowedTypes)

    fun makeQuiz(
        questions: List<Any?>,
        answers: List<Any?>,
        id: Int = questions.indices.random(),
        maxAnswers: Int = 5
    ): QuizData {
        val questionType = info.allowedTypes.random()
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

        return QuizData(
            questionType,
            question.toString(),
            quizAnswers.map { e -> e.toString() }.toList(),
            correctAnswerIndex,
            null
        ).let {
            info.quizzes.add(it)
            it
        }
    }

    fun onSelectAnswer(selectedAnswer: Int) {
        info.quizzes.last().let {
            it.selectedAnswer = selectedAnswer
            it.answered = true
            Log.i("MCQM_oSA", "$it, ${it.isCorrect()}")
        }
        activity.onAnswerSelected()
    }

    class Info(
        val allowedTypes: EnumSet<QuizType>
    ) {
        val quizzes = ArrayList<QuizData>()
    }
}