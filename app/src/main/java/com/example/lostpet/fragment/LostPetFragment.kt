package com.example.lostpet.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.lostpet.Constants
import com.example.lostpet.FormSelectionActivity
import com.example.lostpet.R
import com.example.lostpet.itemAdapter.LostAnimalItem
import com.example.lostpet.viewmodel.HomeLostViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.blushine.android.ui.showcase.MaterialShowcaseView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_lost_pet.*
import kotlinx.android.synthetic.main.fragment_main.*

class LostPetFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val lostViewModel by viewModels<HomeLostViewModel>()

    companion object {
        fun newInstance(): LostPetFragment {
            return LostPetFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lost_pet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lost_recyclerview?.adapter = groupAdapter
        lost_recyclerview?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun bindUI() {
        lostViewModel.itemList.observe(viewLifecycleOwner, Observer {
            updateRecyclerView(it)
        })
        lostViewModel.getLostAnimal()
    }

    private fun updateRecyclerView(itemList: List<LostAnimalItem>) {
        groupAdapter.update(itemList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.RESULT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            MaterialShowcaseView.Builder(requireActivity()).apply {
                setTitleText("The community will help you")
                setContentText("Don't worry, the community takes care of everything!")
                show()
            }
            lostViewModel.getLostAnimal()
        }
    }
}