package com.maxim.wheeloffortune.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.main.MainFragment

class MainActivity : AppCompatActivity(), FragmentManager {
    private val backStackHomeButtonList = mutableListOf<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            replaceFragment(MainFragment(), false, false)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }

    override fun replaceFragment(
        fragment: Fragment,
        addToBackStack: Boolean,
        showBackButton: Boolean
    ) {
        if (addToBackStack) {
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                .addToBackStack(null).commit()
            backStackHomeButtonList.add(showBackButton)
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            if (backStackHomeButtonList.isEmpty())
                backStackHomeButtonList.add(showBackButton)
            else
                backStackHomeButtonList[backStackHomeButtonList.lastIndex] = showBackButton
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
        supportActionBar?.setHomeButtonEnabled(showBackButton)
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
        backStackHomeButtonList.removeAt(backStackHomeButtonList.lastIndex)
        supportActionBar?.setDisplayHomeAsUpEnabled(backStackHomeButtonList[backStackHomeButtonList.lastIndex])
        supportActionBar?.setHomeButtonEnabled(backStackHomeButtonList[backStackHomeButtonList.lastIndex])
    }
}

interface FragmentManager {
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, showBackButton: Boolean)
    fun popBackStack()
}