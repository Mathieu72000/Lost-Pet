package com.example.lostpet.fragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.lostpet.Constants
import com.example.lostpet.GPSTracker
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentFormBinding
import com.example.lostpet.itemAdapter.PictureItem
import com.example.lostpet.viewmodel.FormViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.blushine.android.ui.showcase.MaterialShowcaseSequence
import io.blushine.android.ui.showcase.ShowcaseConfig
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class FormFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val formViewModel by viewModels<FormViewModel>()
    private lateinit var easyImage: EasyImage
    private lateinit var gpsTracker: GPSTracker

    companion object {
        fun newInstance(): FormFragment {
            return FormFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFormBinding>(
            inflater, R.layout.fragment_form, container, false
        ).apply {
            this.lifecycleOwner = this@FormFragment.viewLifecycleOwner
            this.viewmodel = formViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gpsTracker = GPSTracker(requireContext())
        formViewModel.getLoadData()
        this.configureDatePicker()
        this.configureEasyImage()
        form_picture_recyclerView?.adapter = groupAdapter
        this.bindPictureUi()
        this.getGender()
        this.saveForm()
    }

    private fun configureDatePicker() {
        val calendar = Calendar.getInstance()

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfDay, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfDay)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val formatDay = SimpleDateFormat(getString(R.string.dayFormat), Locale.FRANCE)
            form_date_picker.text = formatDay.format(calendar.time)
            formViewModel.formDate.postValue(formatDay.format(calendar.time))
        }

        form_date_picker?.setOnClickListener {
            if (form_date_picker != null) {
                form_date_picker.text = null
                DatePickerDialog(
                    requireContext(),
                    dateListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    private fun getGender() {
        form_gender_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                formViewModel.formGenderId.postValue(parent?.getItemIdAtPosition(position))
            }
        }
    }

    private fun saveForm() {
        form_submit_button?.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                formViewModel.saveForm()
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }
        }
    }

    private fun configureEasyImage() {
        easyImage = EasyImage.Builder(requireContext())
            .allowMultiple(true)
            .setFolderName(getString(R.string.picture_folder_name))
            .setCopyImagesToPublicGalleryFolder(true)
            .build()

        form_upload_photo_button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity?.checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED || activity?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    activity?.requestPermissions(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        Constants.PERMISSION_REQUEST_CODE
                    )
                } else
                    easyImage.openGallery(this@FormFragment)
            }
        }
        form_take_photo_button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity?.checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED || activity?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    activity?.requestPermissions(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        Constants.PERMISSION_REQUEST_CODE
                    )
                } else
                    easyImage.openCameraForImage(this@FormFragment)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    formViewModel.addPictures(imageFiles.toList())
                    formViewModel.latitude = gpsTracker.getLatitude()
                    formViewModel.longitude = gpsTracker.getLongitude()

                    val geoCoder = Geocoder(context, Locale.getDefault())
                    val addresses: List<Address> = geoCoder.getFromLocation(
                        formViewModel.latitude!!,
                        formViewModel.longitude!!,
                        1
                    )
                    formViewModel.location = addresses[0].getAddressLine(0)
                    formViewModel.city = addresses[0].locality
                    formViewModel.state = addresses[0].adminArea
                    formViewModel.postalCode = addresses[0].postalCode
                    formViewModel.country = addresses[0].countryName

                }
            })
    }

    private fun bindPictureUi() {
        formViewModel.itemList.observe(viewLifecycleOwner, Observer {
            updateRecyclerView(it)
        })
    }

    private fun updateRecyclerView(item: List<PictureItem>) {
        groupAdapter.update(item)
    }

    private fun configureShowCaseView() {
        val showcaseConfig = ShowcaseConfig(context)
        showcaseConfig.delay = 100

        MaterialShowcaseSequence(requireActivity(), Constants.SHOWCASE_ID).apply {
            setConfig(showcaseConfig)
            addSequenceItem(
                form_take_photo_button,
                getString(R.string.welcome),
                getString(R.string.firstStep),
                getString(R.string.nextStep)
            )

            addSequenceItem(
                form_upload_photo_button,
                getString(R.string.secondStep),
                getString(R.string.nextStep)
            )
            addSequenceItem(
                form_submit_button,
                getString(R.string.finalStep),
                getString(R.string.confirmation)
            )
            show()
        }
    }
}
