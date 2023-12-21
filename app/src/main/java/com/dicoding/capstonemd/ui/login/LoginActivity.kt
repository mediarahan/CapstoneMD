package com.dicoding.capstonemd.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.capstonemd.Result
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.capstonemd.ui.main.MainActivity
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.databinding.ActivityLoginBinding
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.ui.question.QuestionActivity
import com.dicoding.capstonemd.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    //ui declare
    private lateinit var loginSubmitBtn: Button
    private lateinit var loginEmailEd: EditText
    private lateinit var loginPasswordEd: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewmodel
        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        loginViewModel = viewModels<LoginViewModel> {
            factory
        }.value

        setupView()
        setupAction()

        //ui initialize
        loginSubmitBtn = findViewById(R.id.login_submit_btn)
        loginEmailEd = findViewById(R.id.login_email_ed)
        loginPasswordEd = findViewById(R.id.login_password_ed)

        binding.tvActionRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
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
        binding.loginSubmitBtn.setOnClickListener {
            val email = binding.loginEmailEd.text.toString()
            val password = binding.loginPasswordEd.text.toString()

            loginViewModel.login(email, password).observe(this@LoginActivity) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            val message = "Login was Successful"
                            showToast(message)
                            showLoading(false)

                            val intent = Intent(this@LoginActivity, QuestionActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is Result.Error -> {
                            val message = "Login  was Unsuccessful"
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