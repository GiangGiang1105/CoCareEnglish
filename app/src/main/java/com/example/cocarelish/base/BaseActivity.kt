package com.example.cocarelish.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.facebook.FacebookSdk
import com.google.firebase.auth.FirebaseAuth

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    companion object{
        fun startActivityAnimation(context: Context) {
            if (context is AppCompatActivity) {

            }
        }
    }

    protected lateinit var binding: VB
    protected lateinit var controller: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = getActivityBinding(layoutInflater)
        setContentView(binding.root)

        navHostFragment = getNavHostFragment()
        controller = navHostFragment.findNavController()

        handleTask()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        // update UI trong main activity ( dialog chào mừng)
    }

    abstract fun getActivityBinding(layoutInflater: LayoutInflater): VB

    abstract fun getNavHostFragment(): NavHostFragment

    abstract fun handleTask()
}