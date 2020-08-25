package com.example.lostpet.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.lostpet.Constants
import com.example.lostpet.R
import com.example.lostpet.SearchResultActivity
import com.example.lostpet.databinding.FragmentSearchBinding
import com.example.lostpet.model.Animal
import com.example.lostpet.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

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

        search_button?.setOnClickListener {
            searchViewModel.search()
        }

        searchViewModel.animalList.observe(viewLifecycleOwner, Observer {
            startActivity(Intent(context, SearchResultActivity::class.java).apply {
                putExtra(Constants.SEARCH_RESULT, it as? ArrayList<Animal>)
            })
        })
    }
}