package com.example.lostpet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.lostpet.Constants
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentFormDescriptionBinding
import com.example.lostpet.model.Animal
import com.example.lostpet.viewmodel.FormDescriptionViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.Serializable

class FormDescriptionFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by viewModels<FormDescriptionViewModel>()

    companion object {
        fun newInstance(animal: String?): FormDescriptionFragment {
            return FormDescriptionFragment().apply {
                arguments = bundleOf(Constants.ANIMAL_ID to animal)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFormDescriptionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_form_description, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item = viewModel
        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.description_maps) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        viewModel.animalId.value = arguments?.getString(Constants.ANIMAL_ID)
        viewModel.getData(viewModel.animalId.value ?: "")
        if (googleMap != null) {
            viewModel.getAnimal.observe(viewLifecycleOwner, Observer {
                val animalPosition = LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
                googleMap.addMarker(MarkerOptions().position(animalPosition))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(animalPosition, 16.0F))
            })
        }
    }
}