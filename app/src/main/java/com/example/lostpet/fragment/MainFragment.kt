package com.example.lostpet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.lostpet.R
import com.example.lostpet.itemAdapter.AnimalItem
import com.example.lostpet.viewmodel.HomeViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

abstract class MainFragment<T : HomeViewModel> : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var mainViewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this@MainFragment).get(getViewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    abstract fun getViewModelClass(): Class<T>

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_fragment_recyclerview?.adapter = groupAdapter
        main_fragment_recyclerview?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    activity?.main_fab?.hide()
                } else if (dy < 0) {
                    activity?.main_fab?.show()
                }
            }
        })
        bindUI()
    }

    @ExperimentalCoroutinesApi
    private fun bindUI() {
        mainViewModel.itemList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                updateRecyclerView(it)
            }
            if (it?.isEmpty() == true) {
                Toast.makeText(context, R.string.emptylist, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateRecyclerView(item: List<AnimalItem>) {
        groupAdapter.update(item)
    }
}
