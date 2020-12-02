package com.github.nutyworks.periodicquiz.quiz

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.nutyworks.periodicquiz.*
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*
import kotlin.properties.Delegates

const val SELECTED_QUESTION_TYPE = "com.github.nutyworks.periodicquiz.SELECTED_QUESTION_TYPE"
const val SELECTED_ANSWER_TYPE = "com.github.nutyworks.periodicquiz.SELECTED_ANSWER_TYPE"

class QuizActivity : AppCompatActivity() {

    val quizManager = QuizManager(this, EnumSet.of(QuizType.MULTIPLE_CHOICE))

    var selectedQuestionType by Delegates.notNull<String>()
    var selectedAnswerType by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
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
        indicator.text = quizManager.info.quizzes.filter { it.answered }.let { answered ->
            getString(R.string.correct_wrong_indicator).format(
                answered.filter { it.isCorrect() }.size,
                answered.filter { !it.isCorrect() }.size
            )
        }
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
            .setMessage(getString(R.string.quit_quiz_confirm))
            .setPositiveButton(getString(R.string.positive_answer)) { _, _ ->
                navigateUpTo(Intent(baseContext, MainActivity::class.java))
            }
            .setNegativeButton(getString(R.string.negative_answer)) { _, _ -> }
            .create()
            .show()
    }
}