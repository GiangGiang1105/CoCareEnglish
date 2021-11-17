package com.example.cocarelish.ui.auth

import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseActivity
import com.example.cocarelish.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    override fun getActivityBinding(layoutInflater: LayoutInflater): ActivityAuthBinding =
        ActivityAuthBinding.inflate(layoutInflater)

    override fun getNavHostFragment(): NavHostFragment =
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

    override fun handleTask() {

    }

}