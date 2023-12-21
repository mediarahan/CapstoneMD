package com.dicoding.capstonemd.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.databinding.ActivityMainBinding
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.ui.login.LoginActivity
import com.dicoding.capstonemd.ui.login.LoginViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        //viewmodel
        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        mainViewModel = viewModels<MainViewModel> {
            factory
        }.value

        //Nav Drawer
        val drawerLayout : DrawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        //Bottom Navbar
        val navBottomView: BottomNavigationView = binding.bottomNavView

        //god bless https://stackoverflow.com/questions/59275009/fragmentcontainerview-using-findnavcontroller/59275182#59275182
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        //jangan lupa id fragment navGraph sama bottomNavBar harus sama!
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_chatbot, R.id.nav_history
        ),
            drawerLayout)


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navBottomView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            onNavItemSelected(menuItem)
            true
        }

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val customActionBar = layoutInflater.inflate(R.layout.custom_action_bar, null)
        val customImageView: ImageView = customActionBar.findViewById(R.id.customImageView)
        customImageView.setImageResource(R.drawable.logo)

        supportActionBar?.customView = customActionBar
        supportActionBar?.elevation = 0f
    }

    private fun logout() {
        lifecycleScope.launch {
            mainViewModel.logout()
        }
    }

    private fun onNavItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_logout -> {
                lifecycleScope.launch {
                    logout()
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            // Add more cases if needed for other menu items
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dummy_menu, menu)
        return true
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit App")
        alertDialogBuilder.setMessage("Are you sure you want to exit the app?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            finishAffinity()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }

}
