package com.github.khaledakassem.photo_weather.ui.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.databinding.ActivityMainBinding
import com.github.khaledakassem.photo_weather.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity :MainView,BaseActivity<MainViewModel,ActivityMainBinding>(MainViewModel::class.java){

    private var bottomNavigationPosition: Int = 0

    override fun getLayoutRes()= R.layout.activity_main

    override fun initViewModel(viewModel: MainViewModel) {
            mBinding.viewModel=viewModel
    }

    override fun init(savedInstanceState: Bundle?) {
        initBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.selectedView == -1)
            mBinding.navigation.selectedItemId = R.id.navigation_home
        else
            mBinding.navigation.selectedItemId = viewModel.selectedView
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        navHostFragment.childFragmentManager.fragments[0]
            .onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        navHostFragment.childFragmentManager.fragments[0]
            .onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        when (bottomNavigationPosition) {
            R.id.navigation_home -> finish()
            else -> {
                findNavController(R.id.navHostFragment).navigate(R.id.homeFragment)
                bottomNavigationPosition = R.id.navigation_home
                mBinding.navigation.menu.getItem(0).isChecked = true
            }
        }
    }

    override fun initBottomNavigation(){
        mBinding.navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.navigation_home -> {
                    viewModel.selectedView = R.id.navigation_home
                    findNavController(R.id.navHostFragment).navigate(R.id.homeFragment)
                    bottomNavigationPosition = R.id.navigation_home
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_history_list -> {
                    viewModel.selectedView = R.id.navigation_history_list
                    findNavController(R.id.navHostFragment).navigate(R.id.photoHistoryFragment)
                    bottomNavigationPosition = R.id.navigation_history_list
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_about_us -> {
                    viewModel.selectedView = R.id.navigation_about_us
                    findNavController(R.id.navHostFragment).navigate(R.id.aboutUsFragment)
                    bottomNavigationPosition = R.id.navigation_about_us
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    override fun observeLiveDatas() {

    }

}

