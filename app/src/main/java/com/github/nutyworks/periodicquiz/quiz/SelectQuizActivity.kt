package com.github.nutyworks.periodicquiz.quiz

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.nutyworks.periodicquiz.*
import kotlinx.android.synthetic.main.activity_select_quiz.*
import kotlin.properties.Delegates

const val SELECTED_QUESTION_TYPE = "com.github.nutyworks.periodicquiz.SELECTED_QUESTION_TYPE"
const val SELECTED_ANSWER_TYPE = "com.github.nutyworks.periodicquiz.SELECTED_ANSWER_TYPE"

class SelectQuizActivity : AppCompatActivity() {

    val quizManager = SelectQuizManager(this)

    var selectedQuestionType by Delegates.notNull<String>()
    var selectedAnswerType by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_quiz)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)

        selectedQuestionType = ElementProperty.values()[
            intent.getIntExtra(SELECTED_QUESTION_TYPE, -1)
        ].toString().screamingSnakeCaseToSnakeCase()
        selectedAnswerType = ElementProperty.values()[
                intent.getIntExtra(SELECTED_ANSWER_TYPE, -1)
        ].toString().screamingSnakeCaseToSnakeCase()

        Log.i("SQA_onCreate", "$selectedQuestionType, $selectedAnswerType")

        nextQuiz(selectedQuestionType, selectedAnswerType)
        updateIndicator()
    }

    private fun updateIndicator() {
        indicator.text = getString(R.string.correct_wrong_indicator).format(
            quizManager.results.filter { it }.size,
            quizManager.results.filter { !it }.size
        )
    }

    private fun nextQuiz(questionType: String, answerType: String) {
        val selectQuizFragment = SelectQuizFragment.newInstance(
            quizManager.makeQuiz(
                MainActivity.elements.map { element -> element.asMap()[questionType] },
                MainActivity.elements.map { element -> element.asMap()[answerType] }
            )
        )

        supportFragmentManager.beginTransaction().run {
            setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            replace(R.id.select_quiz_placeholder, selectQuizFragment)
            addToBackStack(null)
            commit()
        }
    }

    fun onAnswerSelected() {
        nextQuiz(selectedQuestionType, selectedAnswerType)
        updateIndicator()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("퀴즈를 종료하시겠습니까?")
            .setPositiveButton("Yes") { _, _ ->
                navigateUpTo(Intent(baseContext, MainActivity::class.java))
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }
}