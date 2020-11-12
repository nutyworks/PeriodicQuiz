package com.github.nutyworks.periodicquiz.quiz.select

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.github.nutyworks.periodicquiz.QuizInfo
import com.github.nutyworks.periodicquiz.R
import kotlinx.android.synthetic.main.fragment_select_quiz.*

private const val ARG_QUIZ_INFO = "quiz_info"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectQuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectQuizFragment : Fragment() {
    private var qi: QuizInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            qi = it.getSerializable(ARG_QUIZ_INFO) as? QuizInfo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val questionText = view.findViewById<TextView>(R.id.question_text)
        val answerGroupLayout = view.findViewById<LinearLayout>(R.id.answer_group_layout)

        if (qi != null) {
            questionText?.text = qi!!.question
            setButtonAnswers(answerGroupLayout!!, qi!!.answerList)
            setButtonHandlers(answerGroupLayout, qi!!.correctAnswer)
        }
    }

    private fun setButtonAnswers(answersLayout: LinearLayout, answers: List<String>) {
        answersLayout.removeAllViews()
        answers.forEach { answer ->
            answersLayout.addView(
                Button(answersLayout.context).apply { this.text = answer }
            )
        }
    }

    private fun setButtonHandlers(answersLayout: LinearLayout, correctAnswer: Int) {
        answersLayout.children.forEachIndexed { i, button ->
            button.setOnClickListener {
                (activity as SelectQuizActivity).quizManager.onSelectAnswer(i)
            }
        }
    }

    fun disableButtons() {
        answer_group_layout.children.forEach { button ->
            button.isEnabled = false
            button.setOnClickListener(null)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        @JvmStatic
        fun newInstance(quizInfo: QuizInfo) =
            SelectQuizFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_QUIZ_INFO, quizInfo)
                }
            }
    }
}