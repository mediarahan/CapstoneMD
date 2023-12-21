package com.dicoding.capstonemd.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.ui.main.MainActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchHiddenGem = findViewById<SwitchMaterial>(R.id.switch_hidden_gem)

        //viewmodel
        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        settingsViewModel = viewModels<SettingsViewModel> {
            factory
        }.value

        settingsViewModel.getHiddenGemSetting().observe(this) { isHiddenGem: Boolean ->
            switchHiddenGem.isChecked = isHiddenGem
        }

        switchHiddenGem.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveHiddenGemSetting(isChecked)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

}