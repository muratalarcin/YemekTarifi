package com.muratalarcin.yemektarifi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.muratalarcin.yemektarifi.R
import com.muratalarcin.yemektarifi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navigationController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.listFragment, R.id.detailFragment, R.id.profilFragment, R.id.favoriteFragment)
        )
        setupActionBarWithNavController(navigationController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }
}
