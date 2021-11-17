package com.example.cocarelish.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseActivity
import com.example.cocarelish.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getActivityBinding(layoutInflater: LayoutInflater): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)

    override fun getNavHostFragment(): NavHostFragment =
        supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment

    override fun handleTask() {
        binding.bottomNavigationView.setupWithNavController(controller)
    }

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
}