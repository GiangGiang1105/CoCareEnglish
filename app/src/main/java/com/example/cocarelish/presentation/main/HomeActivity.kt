package com.example.cocarelish.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseActivity
import com.example.cocarelish.databinding.ActivityHomeBinding
import com.facebook.FacebookSdk
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private lateinit var topLevelDestination: Set<Int>
    private lateinit var appBarConfiguration: AppBarConfiguration


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this)
    }

    override fun handleTask() {
        initListener()
        createBottomNavigation()
    }

    private fun createBottomNavigation() {
        binding.bottomNavigationView.setupWithNavController(controller)

//        topLevelDestination = setOf(R.id.homeFragment, R.id.essayFragment, R.id.profileFragment)
    }

    private fun initListener() {
        controller.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.authFragment, R.id.loginFragment, R.id.signUpFragment ->
                    viewVisibility(View.GONE)
                else -> viewVisibility(View.VISIBLE)
            }
        }
    }

    private fun viewVisibility(visibilityType: Int){
        binding.bottomAppBar.visibility = visibilityType
    }
}