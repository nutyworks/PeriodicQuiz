package com.github.nutyworks.periodicquiz.quiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.nutyworks.periodicquiz.ElementProperty
import com.github.nutyworks.periodicquiz.R
import com.github.nutyworks.periodicquiz.screamingSnakeCaseToNormal
import kotlinx.android.synthetic.main.activity_quiz_option.*

class QuizOptionActivity : AppCompatActivity() {

    companion object {
        const val DEFAULT_QUIZ_OPTION_PREF = "QuizOption"
        const val DEFAULT_QUESTION_TYPE_PREF = "QuestionType"
        const val DEFAULT_ANSWER_TYPE_PREF = "AnswerType"
    }

    private var selectedQuestionType = 0
    private var selectedAnswerType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_option)

        val sharedPreferences = getSharedPreferences(DEFAULT_QUIZ_OPTION_PREF, Context.MODE_PRIVATE)


        quiz_start.setOnClickListener {
            startActivity(
                Intent(this, SelectQuizActivity::class.java).apply {
                    putExtra(SELECTED_QUESTION_TYPE, selectedQuestionType)
                    putExtra(SELECTED_ANSWER_TYPE, selectedAnswerType)
                }
            )
        }

        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ElementProperty.values().filter { !it.deprecated }
                .map(ElementProperty::toString)
                .map(String::screamingSnakeCaseToNormal)
        ).apply {
            this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }.let {
            question_as_spinner.adapter = it
            answer_as_spinner.adapter = it
        }

        question_as_spinner.setSelection(sharedPreferences.getInt(DEFAULT_QUESTION_TYPE_PREF, 0))
        question_as_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedQuestionType = position
                sharedPreferences.edit().let {
                    it.putInt(DEFAULT_QUESTION_TYPE_PREF, position)
                    it.commit()
                }
                Log.i("QOA_qas_OISL", "selectedQuestionType $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        answer_as_spinner.setSelection(sharedPreferences.getInt(DEFAULT_ANSWER_TYPE_PREF, 0))
        answer_as_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedAnswerType = position
                sharedPreferences.edit().let {
                    it.putInt(DEFAULT_ANSWER_TYPE_PREF, position)
                    it.commit()
                }
                Log.i("QOA_aas_OISL", "selectedAnswerType $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}

