package com.example.lostpet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.lostpet.Constants
import com.example.lostpet.R
import com.example.lostpet.itemAdapter.AnimalItem
import com.example.lostpet.model.Animal
import com.example.lostpet.viewmodel.SearchResultViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_search_result.*

class SearchResultFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val resultViewModel by viewModels<SearchResultViewModel>()

    companion object {
        fun newInstance(animalResult: List<Animal>?): SearchResultFragment {
            val searchResult = SearchResultFragment()
            val bundle = Bundle()
            bundle.putSerializable(Constants.SEARCH_RESULT, animalResult as? ArrayList<Animal>)
            searchResult.arguments = bundle
            return searchResult
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_result_recyclerview.adapter = groupAdapter
        bindUI()
        resultViewModel.animal.postValue(
            arguments?.get(Constants.SEARCH_RESULT) as? ArrayList<Animal> ?: mutableListOf()
        )
        resultViewModel.animal.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                no_result_picture?.visibility = View.VISIBLE
            }
        })
    }

    private fun bindUI() {
        resultViewModel.itemList.observe(viewLifecycleOwner, Observer {
            updateRecyclerView(it)
        })
    }

    private fun updateRecyclerView(items: List<AnimalItem>) {
        groupAdapter.update(items)
    }
}