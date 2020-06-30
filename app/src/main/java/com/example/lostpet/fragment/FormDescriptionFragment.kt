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
import com.example.lostpet.viewmodel.FormDescriptionViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FormDescriptionFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by viewModels<FormDescriptionViewModel>()

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
        val binding: FragmentFormDescriptionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_form_description, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item = viewModel
        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.description_maps) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.animalId.value = arguments?.getLong(Constants.ANIMAL_ID) ?: 0
        viewModel.getData(viewModel.animalId.value ?: 0)
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        if (googleMap != null) {
            viewModel.getAnimal.observe(viewLifecycleOwner, Observer {
                if (it?.animal?.latitude != null && it.animal.longitude != null) {
                    val animalPosition = LatLng(it.animal.latitude, it.animal.longitude)
                    googleMap.addMarker(MarkerOptions().position(animalPosition))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(animalPosition, 16.0F))
                }
            })
        }
    }
}