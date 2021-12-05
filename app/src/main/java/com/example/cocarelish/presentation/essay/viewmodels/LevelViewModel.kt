package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.utils.Title.HOME_WORK
import com.example.cocarelish.utils.Title.IELTS_WRITING_TASK_1
import com.example.cocarelish.utils.Title.IELTS_WRITING_TASK_2
import com.example.cocarelish.utils.listTemplate.MenuItem

class LevelViewModel(application: Application) : CommonViewModel(application) {

    val listMenuItem = listOf(
        MenuItem(IELTS_WRITING_TASK_1),
        MenuItem(IELTS_WRITING_TASK_2),
        MenuItem(HOME_WORK),
    )

    override fun onNavigate(itemMenu: MenuItem) {
        when (itemMenu.titleID) {
//            IELTS_WRITING_TASK_1 ->
        }
    }
}