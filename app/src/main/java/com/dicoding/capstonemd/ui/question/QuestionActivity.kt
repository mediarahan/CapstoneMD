package com.dicoding.capstonemd.ui.question

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.data.remote.retrofit.ApiService
import com.dicoding.capstonemd.databinding.ActivityQuestionBinding
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//行きましょう！！！！

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var questionViewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //viewmodel
        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        questionViewModel = viewModels<QuestionViewModel> {
            factory
        }.value

        binding.inputAgeEd
        binding.inputHeightEd
        binding.inputWeightEd
        binding.inputGenderMaleRadio
        binding.inputGenderFemaleRadio
        binding.inputHalalCheck

        val spinnerActivity = binding.inputActivitySpinner
        val activityAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.activity_levels,
            android.R.layout.simple_spinner_item
        )
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerActivity.adapter = activityAdapter

        //WEIGHT LOSS INPUT
        val spinnerWeightLoss = binding.inputWeightlossSpinner
        val weightLossAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.weight_loss_options,
            android.R.layout.simple_spinner_item
        )
        weightLossAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerWeightLoss.adapter = weightLossAdapter

        //INPUT DISEASE
        val spinnerDisease = binding.inputDiseaseSpinner
        val diseaseAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.disease_options,
            android.R.layout.simple_spinner_item
        )
        diseaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDisease.adapter = diseaseAdapter

        //SUBMIT + INPUT SEMUANYA
        val buttonSave = binding.buttonSave
        buttonSave.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    // Get input values
                    val age = binding.inputAgeEd.text.toString().toIntOrNull() ?: 0
                    val activity = binding.inputActivitySpinner.selectedItem.toString()
                    val allergenInput = binding.inputAllergenEd.text.toString()
                    val allergens = allergenInput.split(",").map { it.trim() }
                    val disease = binding.inputDiseaseSpinner.selectedItem.toString()
                    val gender = if (binding.inputGenderMaleRadio.isChecked) "Male" else "Female"
                    val halal = binding.inputHalalCheck.isChecked
                    val height = binding.inputHeightEd.text.toString().toIntOrNull() ?: 0
                    val numberOfMeals =
                        if (binding.inputMeal3Radio.isChecked) 3 else if (binding.inputMeal4Radio.isChecked) 4 else 5
                    val weight = binding.inputWeightEd.text.toString().toIntOrNull() ?: 0
                    val weightLoss = binding.inputWeightlossSpinner.selectedItem.toString()

                    //object
                    val preferences = ApiService.UserPreferences(
                        activity = activity,
                        age = age,
                        allergen = allergens,
                        disease = disease,
                        gender = gender,
                        halal = halal,
                        height = height,
                        number_of_meals = numberOfMeals,
                        weight = weight,
                        weight_loss = weightLoss
                    )

                    // Create the final payload with 'preferences' nested
                    val userPreferences = mapOf("preferences" to preferences)

                    // Call ViewModel function to update preferences
                    questionViewModel.updateUserPreferences(userPreferences)

                    delay(3000)

                    // Move to the next activity or perform other actions
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        //toolbar stuff
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val customActionBar = layoutInflater.inflate(R.layout.custom_action_bar, null)
        val customImageView: ImageView = customActionBar.findViewById(R.id.customImageView)
        customImageView.setImageResource(R.drawable.logo)

        supportActionBar?.customView = customActionBar
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dummy_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
