package com.example.lostpet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.batch.android.Batch
import com.example.lostpet.Constants
import com.example.lostpet.LoginActivity
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentSettingsBinding
import com.example.lostpet.viewmodel.SettingsViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_item.*
import kotlinx.android.synthetic.main.fragment_settings.*
import timber.log.Timber

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
        this.deleteUser()
        this.enableNotifications()
        this.restoreNotificationsState()
    }

    @SuppressLint("PrivateResource")
    private fun disconnectUser() {
        context?.let { context ->
            settings_disconnect_button?.setOnClickListener {
                val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> AuthUI.getInstance()
                            .signOut(context)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    context,
                                    "You have been successfully disconnected.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(
                                        context,
                                        LoginActivity::class.java
                                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                )
                                activity?.finish()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "An error has occurred, try again later.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        DialogInterface.BUTTON_NEGATIVE -> null
                    }
                }
                MaterialAlertDialogBuilder(
                    context,
                    R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
                ).apply {
                    setMessage("Do you want to log out?")
                    setPositiveButton("Yes", dialogClickListener)
                    setNegativeButton("No", dialogClickListener)
                    create().show()
                }
            }
        }
    }

    @SuppressLint("PrivateResource")
    private fun deleteUser() {
        context?.let { context ->
            settings_delete_button?.setOnClickListener {
                val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> AuthUI.getInstance()
                            .delete(context)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    context,
                                    "Your account has been successfully deleted.",
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(
                                    Intent(
                                        context,
                                        LoginActivity::class.java
                                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                )
                                activity?.finish()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "An error has occurred, try again later.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        DialogInterface.BUTTON_NEGATIVE -> null
                    }
                }
                MaterialAlertDialogBuilder(
                    context,
                    R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
                ).apply {
                    setMessage("Are you sure that you want to delete your account?")
                    setPositiveButton("Yes", dialogClickListener)
                    setNegativeButton("No", dialogClickListener)
                    create().show()
                }
            }
        }
    }

    private fun enableNotifications(){
        val editor = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES_NOTIFICATION, Context.MODE_PRIVATE)?.edit()
        notification_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            activity?.let { context ->
                if (isChecked) {
                    editor?.putString(Constants.SHARED_PREFERENCES_SWITCH, Constants.SWITCH_STATE)
                    editor?.apply()
                    notification_switch.isChecked = true
                    Batch.optIn(context)
                    Batch.onStart(context)
                } else {
                    editor?.clear()?.apply()
                    notification_switch.isChecked = false
                    Batch.optOut(context)
                }
            }
        }
    }

    private fun restoreNotificationsState(){
        val preferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES_NOTIFICATION, Context.MODE_PRIVATE)
        val switch = preferences?.getString(Constants.SHARED_PREFERENCES_SWITCH, "")
        if(switch.equals(Constants.SWITCH_STATE)) {
            notification_switch.isChecked = true
        }
    }
}