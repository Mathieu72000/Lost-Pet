package com.example.lostpet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.lostpet.Constants
import com.example.lostpet.R

class FormDescriptionFragment : Fragment() {

    companion object {
        fun newInstance(animalId: Long?): FormDescriptionFragment {
            return FormDescriptionFragment().apply {
                arguments = bundleOf(Constants.ANIMAL_ID to animalId)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_description, container, false)
    }
}