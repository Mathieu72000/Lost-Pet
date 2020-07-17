package com.example.lostpet.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lostpet.Constants
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentFormLostBinding
import com.example.lostpet.viewmodel.FormLostViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import io.blushine.android.ui.showcase.MaterialShowcaseView
import io.blushine.android.ui.showcase.ShowcaseListener
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.android.synthetic.main.fragment_form.form_gender_spinner
import kotlinx.android.synthetic.main.fragment_form_lost.*
import kotlinx.android.synthetic.main.fragment_form_lost.form_submit_button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.EasyImage
import java.text.SimpleDateFormat
import java.util.*

class FormLostFragment : Fragment() {

    private val formLostViewModel by viewModels<FormLostViewModel>()
    private lateinit var easyImage: EasyImage

    companion object {
        fun newInstance(): FormLostFragment {
            return FormLostFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFormLostBinding>(
            inflater,
            R.layout.fragment_form_lost,
            container,
            false
        ).apply {
            this.lifecycleOwner = this@FormLostFragment.viewLifecycleOwner
            this.viewmodel = formLostViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.configureEasyImage()
        this.configureDatePicker()
        this.configurePlaceAutoComplete()
        this.saveForm()
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
                formLostViewModel.formLostGenderId.postValue(parent?.getItemIdAtPosition(position))
            }
        }
    }

    private fun configurePlaceAutoComplete() {
        form_lost_autocomplete.setOnClickListener {
            if (form_lost_autocomplete != null) form_lost_autocomplete.text = null
            val fields: List<Place.Field> = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val intent: Intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(requireContext())
            startActivityForResult(intent, Constants.AUTOCOMPLETE_REQUEST_CODE)
        }
    }

    private fun configureDatePicker() {
        val calendar = Calendar.getInstance()

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfDay, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfDay)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val formatDay = SimpleDateFormat(getString(R.string.dayFormat), Locale.FRANCE)
            form_lost_date_picker.text = formatDay.format(calendar.time)
            formLostViewModel.formLostDate.postValue(formatDay.format(calendar.time))
        }

        form_lost_date_picker?.setOnClickListener {
            form_lost_date_picker.text = null
            DatePickerDialog(
                requireContext(),
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    //    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private fun configureEasyImage() {
        easyImage = EasyImage.Builder(requireContext())
            .allowMultiple(true)
            .setFolderName(getString(R.string.picture_folder_name))
            .setCopyImagesToPublicGalleryFolder(true)
            .build()

        form_lost_upload_picture.setOnClickListener {
            easyImage.openGallery(this)
        }
    }

    private fun saveForm() {
        form_submit_button?.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                formLostViewModel.saveForm()
            }
            activity?.let {
                MaterialShowcaseView.Builder(it).apply {
                    setTitleText("Don't worry")
                    setContentText("The community will help you tou find your pet!")
                    addListener(object : ShowcaseListener {
                        override fun onShowcaseSkipped(p0: MaterialShowcaseView?) {

                        }

                        override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {
                            onResume()
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val place: Place? = data?.let { Autocomplete.getPlaceFromIntent(it) }
            formLostViewModel.formLostLocation.postValue(place?.address)
            formLostViewModel.longitude = place?.latLng?.longitude
            formLostViewModel.latitude = place?.latLng?.latitude
        }
    }
}