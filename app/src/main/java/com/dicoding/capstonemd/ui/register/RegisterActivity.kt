package com.dicoding.capstonemd.ui.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.capstonemd.ui.main.MainActivity
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.Result
import com.dicoding.capstonemd.databinding.ActivityRegisterBinding
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.ui.login.LoginActivity
import com.dicoding.capstonemd.ui.verify.VerifyActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var registerSubmitBtn: Button
    private lateinit var registerNameEd: EditText
    private lateinit var registerEmailEd: EditText
    private lateinit var registerPasswordEd: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // viewmodel
        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        registerViewModel = viewModels<RegisterViewModel> {
            factory
        }.value

        // Hide action bar and status bar
        setupView()

        // Check if the user is already logged in
        registerViewModel.isUserLoggedIn.observe(this) { isUserLoggedIn ->
            Log.d("LiveData", "isUserLoggedIn changed: $isUserLoggedIn")
            if (isUserLoggedIn == true) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                setupAction()
                binding.tvActionLogin.setOnClickListener {
                    // Intent to login
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }

                registerSubmitBtn = findViewById(R.id.register_submit_btn)
                registerNameEd = findViewById(R.id.register_name_ed)
                registerEmailEd = findViewById(R.id.register_email_ed)
                registerPasswordEd = findViewById(R.id.register_password_ed)
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.registerSubmitBtn.setOnClickListener {
            val name = binding.registerNameEd.text.toString()
            val email = binding.registerEmailEd.text.toString()
            val password = binding.registerPasswordEd.text.toString()

            registerViewModel.register(name, email, password).observe(this@RegisterActivity) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            val message = "Register was Successful"
                            showToast(message)
                            showLoading(false)

                            // Finished register, move to verify activity
                            val intent = Intent(this@RegisterActivity, VerifyActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }

                        is Result.Error -> {
                            val message = "Register was Unsuccessful"
                            showToast(message)
                            showLoading(false)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
