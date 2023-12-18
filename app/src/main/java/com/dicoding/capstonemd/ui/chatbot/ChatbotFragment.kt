package com.dicoding.capstonemd.ui.chatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.databinding.FragmentChatbotBinding

class ChatbotFragment : Fragment() {

    private lateinit var binding: FragmentChatbotBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

}