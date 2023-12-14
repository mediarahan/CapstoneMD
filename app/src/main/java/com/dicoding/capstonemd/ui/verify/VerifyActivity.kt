package com.dicoding.capstonemd.ui.verify

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.capstonemd.MainActivity
import com.dicoding.capstonemd.Result
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.databinding.ActivityVerifyBinding
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.ui.login.LoginActivity

class VerifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyBinding
    private lateinit var verifyViewModel: VerifyViewModel

    private lateinit var submitButton: Button
    private lateinit var codeEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewmodel
        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        verifyViewModel = viewModels<VerifyViewModel> {
            factory
        }.value

        setupView()
        setupAction()

        submitButton = findViewById(R.id.verify_submit_btn)
        codeEditText = findViewById(R.id.verify_code_ed)
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
        binding.verifySubmitBtn.setOnClickListener {
            val code = binding.verifyCodeEd.text.toString()
            val email = intent.getStringExtra("email")

            //pake null check
            if (email != null) {
                verifyViewModel.verify(email, code).observe(this@VerifyActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }

                            is Result.Success -> {
                                val message = "Verification was Successful"
                                showToast(message)
                                showLoading(false)

                                //Selesai register pindah ke menu login
                                val intent = Intent(this@VerifyActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            is Result.Error -> {
                                val message = "Verification  was Unsuccessful"
                                showToast(message)
                                showLoading(false)
                            }
                        }
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