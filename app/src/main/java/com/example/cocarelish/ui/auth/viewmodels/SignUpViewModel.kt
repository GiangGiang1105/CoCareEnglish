package com.example.cocarelish.ui.auth.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.utils.Title
import kotlinx.coroutines.launch

class SignUpViewModel:CommonViewModel() {
    override fun onNavigate(itemTitle: Int) {
        viewModelScope.launch {
            when(itemTitle){
                Title.AUTH_LOGIN -> evenSender.send(CommonEvent.OnNavigation(R.id.action_signUpFragment_to_loginFragment))
            }
        }
    }
}