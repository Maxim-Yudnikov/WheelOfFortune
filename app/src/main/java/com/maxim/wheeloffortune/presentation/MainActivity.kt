package com.maxim.wheeloffortune.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.main.MainFragment

class MainActivity : AppCompatActivity(), FragmentManager {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(MainFragment(), false)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }

    override fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        if (addToBackStack)
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                .addToBackStack(null).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }
}

interface FragmentManager {
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean)
    fun popBackStack()
}