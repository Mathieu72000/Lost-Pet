package com.example.lostpet.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lostpet.Constants
import com.example.lostpet.LoginActivity
import com.example.lostpet.MainActivity
import com.example.lostpet.R
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_disconnect_button?.setOnClickListener {
            disconnectUser()
        }
    }

    private fun disconnectUser(){
        AuthUI.getInstance()
            .signOut(requireContext())
            .addOnSuccessListener {
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
    }
}