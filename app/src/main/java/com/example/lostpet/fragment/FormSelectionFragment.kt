package com.example.lostpet.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lostpet.FormActivity
import com.example.lostpet.FormLostActivity
import com.example.lostpet.R
import kotlinx.android.synthetic.main.fragment_form_selection.*

class FormSelectionFragment : Fragment() {

    companion object {
        fun newInstance(): FormSelectionFragment {
            return FormSelectionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        found_button?.setOnClickListener {
            val formIntent = Intent(context, FormActivity::class.java)
            formIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            startActivity(formIntent)
            activity?.finish()
        }

        lost_button?.setOnClickListener {
            val formLostIntent = Intent(context, FormLostActivity::class.java)
            formLostIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            startActivity(formLostIntent)
            activity?.finish()
        }
    }
}