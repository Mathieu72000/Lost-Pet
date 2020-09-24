package com.example.lostpet.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.lostpet.R
import com.example.lostpet.SearchResultActivity
import com.example.lostpet.databinding.FragmentSearchBinding
import com.example.lostpet.model.Animal
import com.example.lostpet.utils.Constants
import com.example.lostpet.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class SearchFragment : Fragment() {

    private val searchViewModel by viewModels<SearchViewModel>()

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSearchBinding>(
            inflater,
            R.layout.fragment_search,
            container,
            false
        ).apply {
            this.lifecycleOwner = this@SearchFragment.viewLifecycleOwner
            this.viewmodel = searchViewModel
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        micro_species.setOnClickListener {
            startSpeechToText(Constants.VOICE_SPECIES)

        }

        micro_breed.setOnClickListener {
            startSpeechToText(Constants.VOICE_BREED)
        }

        micro_name.setOnClickListener {
            startSpeechToText(Constants.VOICE_NAME)
        }

        micro_postalCode.setOnClickListener {
            startSpeechToText(Constants.VOICE_POSTAL_CODE)
        }

        micro_color.setOnClickListener {
            startSpeechToText(Constants.VOICE_COLOR)
        }

        micro_identification.setOnClickListener {
            startSpeechToText(Constants.VOICE_IDENTIFICATION_NUMBER)
        }

        search_button?.setOnClickListener {
            searchViewModel.search()
        }
        searchViewModel.animalList.observe(viewLifecycleOwner, Observer {
            startActivity(Intent(context, SearchResultActivity::class.java).apply {
                putExtra(Constants.SEARCH_RESULT, it as? ArrayList<Animal>)
            })
        })
    }

    private fun startSpeechToText(code: Int) {
        context?.let {
            if (EasyPermissions.hasPermissions(
                    it,
                    Manifest.permission.RECORD_AUDIO
                )
            ) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                    putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...")
                }
                startActivityForResult(intent, code)
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    resources.getString(R.string.voice_rational),
                    Constants.RECORD_VOICE_PERMISSION,
                    Manifest.permission.RECORD_AUDIO
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.VOICE_SPECIES -> {
                val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                searchViewModel.searchSpecies.postValue(result?.get(0))
            }
            Constants.VOICE_BREED -> {
                val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                searchViewModel.searchBreed.postValue(result?.get(0))
            }
            Constants.VOICE_NAME -> {
                val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                searchViewModel.searchName.postValue(result?.get(0))
            }
            Constants.VOICE_POSTAL_CODE -> {
                val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                searchViewModel.searchPostalCode.postValue(result?.get(0))
            }
            Constants.VOICE_COLOR -> {
                val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                searchViewModel.searchColor.postValue(result?.get(0))
            }
            Constants.VOICE_IDENTIFICATION_NUMBER -> {
                val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                searchViewModel.searchIdentificationNumber.postValue(result?.get(0))
            }
        }
    }
}