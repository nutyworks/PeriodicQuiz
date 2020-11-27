package com.github.nutyworks.periodicquiz.quiz.select

import com.github.nutyworks.periodicquiz.QuizInfo

class SelectQuizManager(private val activity: SelectQuizActivity) {
    val results = ArrayList<Boolean>()
    private var recentQuiz: QuizInfo? = null

    fun makeQuiz(questions: List<Any>, answers: List<Any>,
                 id: Int = questions.indices.random(), maxAnswers: Int = 5): QuizInfo {
        val question = questions[id]
        val correctAnswerIndex = (0 until maxAnswers).random()
        val quizAnswers = answers.filterIndexed { index, _ -> id != index }
            .shuffled().subList(0, 4).toMutableList()
        quizAnswers.add(correctAnswerIndex, answers[id])

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