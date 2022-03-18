package com.example.cocarelish.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseActivity
import com.example.cocarelish.databinding.ActivityHomeBinding
import com.example.cocarelish.utils.MyPreference
import com.facebook.FacebookSdk
import dagger.hilt.android.AndroidEntryPoint
import java.text.FieldPosition
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(), IHomeActivity {

    private lateinit var topLevelDestination: Set<Int>
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var myPreference: MyPreference

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
        // Fake user
        myPreference.saveUserID("5")
    }

    override fun handleTask() {
        initListener()
        createBottomNavigation()
    }

    private fun createBottomNavigation() {
//        binding.bottomNavigationView.setupWithNavController(controller)

//        topLevelDestination = setOf(R.id.homeFragment, R.id.essayFragment, R.id.profileFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: $menu")
        menuInflater.inflate(R.menu.bottom_menu, menu)
        binding.bottomNavigationView.setupWithNavController( menu!!,controller)
        return true
    }

    private fun initListener() {
        controller.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.authFragment, R.id.loginFragment, R.id.signUpFragment->
                    viewVisibility(View.GONE)
                else -> viewVisibility(View.VISIBLE)
            }
        }
    }

    override fun navigateToTab(position: Int) {
        if(position < binding.bottomNavigationView.tabs.count()){
            binding.bottomNavigationView.apply {
                selectTab(tabs[position])
            }
        }
    }

    private fun viewVisibility(visibilityType: Int){
        binding.bottomNavigationView.visibility = visibilityType
    }
}