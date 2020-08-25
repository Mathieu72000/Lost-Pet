package com.example.lostpet.fragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.lostpet.Constants
import com.example.lostpet.GPSTracker
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentFormBinding
import com.example.lostpet.itemAdapter.PictureItem
import com.example.lostpet.itemAdapter.SpinnerAdapter
import com.example.lostpet.viewmodel.FormViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.blushine.android.ui.showcase.MaterialShowcaseSequence
import io.blushine.android.ui.showcase.MaterialShowcaseView
import io.blushine.android.ui.showcase.ShowcaseConfig
import io.blushine.android.ui.showcase.ShowcaseListener
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.android.synthetic.main.fragment_form_description.*
import kotlinx.coroutines.awaitAll
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*

class FormFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
        setHasStableIds(true)
    }
    private val formViewModel by viewModels<FormViewModel>()
    private lateinit var easyImage: EasyImage
    private lateinit var gpsTracker: GPSTracker
    private lateinit var receiver: BroadcastReceiver

    companion object {
        fun newInstance(isLost: Boolean, animal: String?): FormFragment {
            val formFragment = FormFragment()
            val bundle = Bundle()
            bundle.putBoolean(Constants.IS_LOST, isLost)
            bundle.putString(Constants.ANIMAL_ID, animal)
            formFragment.arguments = bundle
            return formFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureOnReceived()
        val intentFilter = IntentFilter(Constants.DELETE_PICTURE)
        context?.registerReceiver(receiver, intentFilter)
        formViewModel.displayGenderListFromCloud()
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
        val animalId = arguments?.getString(Constants.ANIMAL_ID)
        if (animalId != null) {
            formViewModel.getLoadData(animalId)
            form_take_photo_button.visibility = View.GONE
        }
        this.getUserLocationPermission()
        context?.let {
            gpsTracker = GPSTracker(it)
        }
        this.getGender()
        this.configureShowCaseView()
        this.configureDatePicker()
        this.configureEasyImage()
        this.configurePlaceAutoComplete()
        form_picture_recyclerView?.adapter = groupAdapter
        this.bindPictureUi(animalId)

        formViewModel.isLost = arguments?.getBoolean(Constants.IS_LOST) ?: false

        if (!formViewModel.isLost) {
            formViewModel.viewModelState.observe(viewLifecycleOwner, Observer {
                if (it == FormViewModel.State.IS_FINISH) {
                    activity?.let {
                        MaterialShowcaseView.Builder(it).apply {
                            setTitleText("Thanks for your help !")
                            setContentText("Thanks to you, this animal will surely find his owner!")
                            addListener(object : ShowcaseListener {
                                override fun onShowcaseSkipped(p0: MaterialShowcaseView?) {

                                }

                                override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {
                                }

                                override fun onTargetPressed(p0: MaterialShowcaseView?) {
                                }

                                override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                                    activity?.setResult(Activity.RESULT_OK)
                                    activity?.finish()
                                }

                            })
                            show()
                        }
                    }
                }
            })
            this.saveForm()
        } else {
            form_take_photo_button?.visibility = View.INVISIBLE
            form_upload_photo_button?.visibility = View.VISIBLE
            form_autocomplete?.visibility = View.VISIBLE

            formViewModel.viewModelState.observe(viewLifecycleOwner, Observer {
                if (it == FormViewModel.State.IS_FINISH) {
                    activity?.let {
                        MaterialShowcaseView.Builder(it).apply {
                            setTitleText("Don't worry !")
                            setContentText("The community will help you")
                            addListener(object : ShowcaseListener {
                                override fun onShowcaseSkipped(p0: MaterialShowcaseView?) {

                                }

                                override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {
                                }

                                override fun onTargetPressed(p0: MaterialShowcaseView?) {
                                }

                                override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                                    activity?.setResult(Activity.RESULT_OK)
                                    activity?.finish()
                                }

                            })
                            show()
                        }
                    }
                }
            })
            this.saveLostForm()
        }
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
            context?.let {
                if (form_date_picker != null) {
                    form_date_picker.text = null
                    DatePickerDialog(
                        it,
                        dateListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        }
    }

    private fun getGender() {
        form_gender_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                formViewModel.formGender.postValue(parent?.getItemAtPosition(position) as String?)
            }
        }
    }

    private fun saveForm() {
        form_submit_button?.setOnClickListener {
            formViewModel.saveForm()
        }
    }

    private fun saveLostForm() {
        form_submit_button?.setOnClickListener {
            formViewModel.saveLostForm()
        }
    }

    private fun getUserLocationPermission() {
        context?.let {
            if (EasyPermissions.hasPermissions(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                onResume()
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    resources.getString(R.string.rational),
                    Constants.REQUEST_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun configureEasyImage() {
        activity?.let { context ->
            easyImage = EasyImage.Builder(context)
                .allowMultiple(true)
                .setFolderName(getString(R.string.picture_folder_name))
                .setCopyImagesToPublicGalleryFolder(true)
                .build()

            form_take_photo_button.setOnClickListener {

                if (EasyPermissions.hasPermissions(
                        context,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    easyImage.openCameraForImage(this)
                } else {
                    EasyPermissions.requestPermissions(
                        context,
                        getString(R.string.take_photo_permission),
                        Constants.CAMERA_PERMISSION_REQUEST,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }

            }
            form_upload_photo_button.setOnClickListener {
                if (EasyPermissions.hasPermissions(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    easyImage.openGallery(this)
                } else {
                    EasyPermissions.requestPermissions(
                        context,
                        getString(R.string.upload_photo_permission),
                        Constants.WRITE_EXTERNAL_STORAGE_REQUEST,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val place: Place? = data?.let { Autocomplete.getPlaceFromIntent(it) }
            formViewModel.formLocation.postValue(place?.address)
            formViewModel.longitude = place?.latLng?.longitude
            formViewModel.latitude = place?.latLng?.latitude
        }
        activity?.let {
            easyImage.handleActivityResult(
                requestCode,
                resultCode,
                data,
                it,
                object : DefaultCallback() {
                    override fun onMediaFilesPicked(
                        imageFiles: Array<MediaFile>,
                        source: MediaSource
                    ) {
                        formViewModel.addPhoto(imageFiles.toList())

                        if (formViewModel.isLost == false) {
                            formViewModel.latitude = gpsTracker.getLatitude()
                            formViewModel.longitude = gpsTracker.getLongitude()

                            val geoCoder = Geocoder(context, Locale.getDefault())
                            val addresses: List<Address> = geoCoder.getFromLocation(
                                formViewModel.latitude ?: 0.0,
                                formViewModel.longitude ?: 0.0,
                                1
                            )
                            formViewModel.location = addresses[0].getAddressLine(0)
                            formViewModel.city = addresses[0].locality
                            formViewModel.state = addresses[0].adminArea
                            formViewModel.postalCode = addresses[0].postalCode
                            formViewModel.country = addresses[0].countryName
                        }
                    }
                })
        }
    }

    private fun bindPictureUi(animalId: String?) {
        formViewModel.itemList.observe(viewLifecycleOwner, Observer {
            updateRecyclerView(it)
        })
        if (animalId != null) {
            restoreGender()
        }
    }

    private fun updateRecyclerView(item: List<PictureItem>) {
        groupAdapter.update(item)
    }

    private fun configureOnReceived() {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val position = intent?.getIntExtra(Constants.PICTURE_POSITION, 0)
                if (position != null) {
                    formViewModel.removePictures(position)
                }
            }
        }
    }

    private fun restoreGender() {
        formViewModel.formGender.observe(viewLifecycleOwner, Observer { gender ->
            (form_gender_spinner.adapter as? SpinnerAdapter<String>)?.let {
                val indexOfFirst = it.item.indexOfFirst {
                    it == gender
                }
                if (indexOfFirst != -1) {
                    form_gender_spinner.setSelection(indexOfFirst)
                }
                if (form_gender_spinner?.onItemSelectedListener == null) {
//                    this.getGender()
                }
            }
        })
    }

    private fun configurePlaceAutoComplete() {
        context?.let { context ->
            form_autocomplete.setOnClickListener {
                if (form_autocomplete != null) form_autocomplete.text = null
                val fields: List<Place.Field> = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
                val intent: Intent =
                    Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(context)
                startActivityForResult(intent, Constants.AUTOCOMPLETE_REQUEST_CODE)
            }
        }
    }

    private fun configureShowCaseView() {
        activity?.let {
            val showcaseConfig = ShowcaseConfig(it)
            showcaseConfig.delay = 70

            MaterialShowcaseSequence(it, Constants.SHOWCASE_ID).apply {
                setConfig(showcaseConfig)
                addSequenceItem(
                    form_take_photo_button,
                    getString(R.string.welcome),
                    getString(R.string.firstStep),
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(receiver)
    }
}
