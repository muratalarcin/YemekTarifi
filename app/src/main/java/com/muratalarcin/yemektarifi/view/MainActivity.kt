package com.muratalarcin.yemektarifi.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
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
        setContentView(R.layout.splash_layout)

        // Animasyonu başlat
        val animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in)
        findViewById<RelativeLayout>(R.id.splash_layout).startAnimation(animation)
        // Splash ekranını göstermek ve ana aktiviteye geçmek için Handler kullanımı
        Handler(Looper.getMainLooper()).postDelayed({
            // Ana aktiviteyi başlat
            startApp()
        }, SPLASH_DURATION.toLong())
    }

    private fun startApp() {
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

    companion object {
        private const val SPLASH_DURATION = 2000 // saniye
    }
}

