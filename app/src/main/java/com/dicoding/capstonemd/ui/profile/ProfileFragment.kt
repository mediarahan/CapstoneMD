package com.dicoding.capstonemd.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.capstonemd.databinding.FragmentProfileBinding
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore
import com.dicoding.capstonemd.ui.question.QuestionActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UserPreference
        userPreference = UserPreference.getInstance(requireContext().dataStore)

// Observe changes to both user email and display name
        lifecycleScope.launchWhenStarted {
            userPreference.getUserEmail().collect { userEmail ->
                binding.tvEmail.text = userEmail

                userPreference.getUserDisplayName().collect { userDisplayName ->
                    binding.tvName.text = userDisplayName
                }
            }
        }

        binding.profilingBtn.setOnClickListener {
            val intent = Intent(requireContext(), QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}
