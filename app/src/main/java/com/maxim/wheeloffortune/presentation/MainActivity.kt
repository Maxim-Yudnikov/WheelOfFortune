package com.maxim.wheeloffortune.presentation

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.main.MainFragment

class MainActivity : AppCompatActivity(), FragmentManager {
    private var backStackHomeButtonList = mutableListOf<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (getSystemService(Context.UI_MODE_SERVICE) as UiModeManager).setApplicationNightMode(
                UiModeManager.MODE_NIGHT_AUTO
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            replaceFragment(MainFragment(), false, false)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(
            LIST,
            ArrayList(backStackHomeButtonList.map { if (it) "1" else "0" })
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        backStackHomeButtonList =
            savedInstanceState.getStringArrayList(LIST)?.map { it == "1" }!!
                .toMutableList()
        supportActionBar?.setDisplayHomeAsUpEnabled(backStackHomeButtonList[backStackHomeButtonList.lastIndex])
        supportActionBar?.setHomeButtonEnabled(backStackHomeButtonList[backStackHomeButtonList.lastIndex])
    }

    companion object {
        private const val LIST = "LIST"
    }
}

interface FragmentManager {
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, showBackButton: Boolean)
    fun popBackStack()
}