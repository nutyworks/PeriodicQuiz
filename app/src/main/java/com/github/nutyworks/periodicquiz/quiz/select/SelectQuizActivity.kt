package com.github.nutyworks.periodicquiz.quiz.select

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.nutyworks.periodicquiz.MainActivity
import com.github.nutyworks.periodicquiz.R
import kotlinx.android.synthetic.main.activity_select_quiz.*

class SelectQuizActivity : AppCompatActivity() {

    val quizManager = SelectQuizManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_quiz)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)

        nextQuiz()
        updateIndicator()
    }

    private fun updateIndicator() {
        indicator.text = getString(R.string.correct_wrong_indicator).format(
            quizManager.results.filter { it }.size,
            quizManager.results.filter { !it }.size
        )
    }

    private fun nextQuiz() {
        val selectQuizFragment = SelectQuizFragment.newInstance(
            quizManager.makeQuiz(
                MainActivity.elements.map { element -> element.melt },
                MainActivity.elements.map { element -> element.boil }
            )
        )

        supportFragmentManager.beginTransaction().run {
            setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            replace(R.id.select_quiz_placeholder, selectQuizFragment)
            addToBackStack(null)
            commit()
        }
    }

    fun onAnswerSelected() {
        nextQuiz()
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