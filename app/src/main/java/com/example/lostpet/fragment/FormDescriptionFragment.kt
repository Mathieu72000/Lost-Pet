package com.example.lostpet.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
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
import com.example.lostpet.itemAdapter.PictureItem
import com.example.lostpet.viewmodel.FormDescriptionViewModel
import com.example.lostpet.viewmodel.PictureViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_form_description.*
import pub.devrel.easypermissions.EasyPermissions

class FormDescriptionFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by viewModels<FormDescriptionViewModel>()
    private var groupAdapter = GroupAdapter<GroupieViewHolder>()

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
        bindUI()
        activity?.let { context ->
            phone_button?.setOnClickListener {
                if (EasyPermissions.hasPermissions(context, Manifest.permission.CALL_PHONE)) {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:${viewModel.getAnimal.value?.userPhone}")
                    activity?.startActivity(callIntent)
                } else {
                    EasyPermissions.requestPermissions(
                        context,
                        "permission",
                        Constants.PHONE_CALL_PERMISSION,
                        Manifest.permission.CALL_PHONE
                    )
                }
            }
        }
        email_button?.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:${viewModel.getAnimal.value?.userEmail}")
            activity?.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
        details_picture_recyclerView?.adapter = groupAdapter
        if (googleMap != null) {
            viewModel.getAnimal.observe(viewLifecycleOwner, Observer {
                val animalPosition = LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
                googleMap.addMarker(MarkerOptions().position(animalPosition))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(animalPosition, 16.0F))
            })
        }
    }

    private fun bindUI() {
        viewModel.getAnimal.observe(viewLifecycleOwner, Observer {
            it?.pictureList?.map {
                PictureItem(PictureViewModel(it))
            }?.let { it1 -> updateRecyclerView(it1) }
        })
    }

    private fun updateRecyclerView(item: List<PictureItem>) {
        groupAdapter.update(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}