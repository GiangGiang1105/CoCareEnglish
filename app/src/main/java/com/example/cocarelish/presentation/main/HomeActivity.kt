package com.example.cocarelish.presentation.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseActivity
import com.example.cocarelish.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getActivityBinding(layoutInflater: LayoutInflater): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)

    override fun getNavHostFragment(): NavHostFragment =
        supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment


    companion object {
        val TAG: String = HomeActivity::class.java.name

        fun start(context: Context) {
            if (context is AppCompatActivity) {
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
                startActivityAnimation(context)
            }
        }
    }

    override fun handleTask() {
        initListener()
    }

    private fun initListener() {
        controller.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.authFragment, R.id.loginFragment, R.id.signUpFragment ->
                    viewVisibility(View.GONE)
            }
        }
    }

    private fun viewVisibility(visibilityType: Int){
        binding.bottomAppBar.visibility = visibilityType
    }
}