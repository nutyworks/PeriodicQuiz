package com.github.nutyworks.periodicquiz.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.children
import com.github.nutyworks.periodicquiz.R
import kotlinx.android.synthetic.main.fragment_select_quiz.*

private const val ARG_QUIZ_INFO = "quiz_info"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectQuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectQuizFragment : Fragment() {
    private var qd: QuizData? = null
    private var isAnimationFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            qd = it.getSerializable(ARG_QUIZ_INFO) as? QuizData
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
        super.onViewCreated(view, savedInstanceState);

        val answerGroupLayout = view.findViewById<LinearLayout>(R.id.answer_group_layout)

        qd?.let {
            question_text?.text = it.question
            setButtonAnswers(answerGroupLayout!!, it.answers)
            setButtonHandlers(answerGroupLayout, it.correctAnswer as Int)
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var anim = super.onCreateAnimation(transit, enter, nextAnim)

        if (anim == null && nextAnim != 0) {
            anim = AnimationUtils.loadAnimation(activity, nextAnim)
        }

        return anim?.let {
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    isAnimationFinished = false
                }

                override fun onAnimationEnd(animation: Animation?) {
                    isAnimationFinished = true
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })

            it
        }
    }

    private fun setButtonAnswers(answersLayout: LinearLayout, answers: List<String>) {
        answersLayout.removeAllViews()
        answers.forEach { answer ->
            answersLayout.addView(
                Button(answersLayout.context).apply {
                    this.text = answer
                    this.isAllCaps = false
                }
            )
        }
    }

    private fun setButtonHandlers(answersLayout: LinearLayout, correctAnswer: Int) {
        answersLayout.children.forEachIndexed { i, button ->
            button.setOnClickListener {
                Log.d("button onclick", isAnimationFinished.toString())
                if (!it.isEnabled || !isAnimationFinished) return@setOnClickListener

                (activity as QuizActivity).quizManager.onSelectAnswer(i)
                disableButtons(answersLayout)
                it.setOnClickListener(null)
            }
        }
    }

    private fun disableButtons(answersLayout: LinearLayout) {
        answersLayout.children.forEach { button ->
            button.isEnabled = false
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
        fun newInstance(quizData: QuizData) =
            SelectQuizFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_QUIZ_INFO, quizData)
                }
            }
    }
}