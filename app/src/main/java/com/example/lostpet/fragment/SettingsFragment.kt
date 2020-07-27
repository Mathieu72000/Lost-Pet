package com.example.lostpet.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lostpet.LoginActivity
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentSettingsBinding
import com.example.lostpet.viewmodel.SettingsViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val settingsViewModel by viewModels<SettingsViewModel>()

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater, R.layout.fragment_settings, container, false
        ).apply {
            this.lifecycleOwner = this@SettingsFragment.viewLifecycleOwner
            this.item = settingsViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.disconnectUser()
    }

    private fun disconnectUser() {
        settings_disconnect_button?.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnSuccessListener {
                    startActivity(
                        Intent(
                            context,
                            LoginActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                    activity?.finish()
                }
        }
    }
}