package com.example.cocarelish.presentation.grade.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cocarelish.presentation.grade.fragment.AssessmentFragment
import com.example.cocarelish.presentation.grade.fragment.FixEssayFragment
import com.example.cocarelish.presentation.grade.fragment.YourScoreFragment
import com.example.cocarelish.utils.Consts.NUMBER_COUNT
import com.example.cocarelish.utils.Consts.PAGE_0
import com.example.cocarelish.utils.Consts.PAGE_1
import com.example.cocarelish.utils.Consts.PAGE_2

class GradeAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = NUMBER_COUNT

    override fun createFragment(position: Int): Fragment = when (position) {
        PAGE_0 -> AssessmentFragment.newInstance()
        PAGE_1 -> YourScoreFragment.newInstance()
        PAGE_2 -> FixEssayFragment.newInstance()
        else -> throw IndexOutOfBoundsException("Not find fragment correct with position!")
    }
}