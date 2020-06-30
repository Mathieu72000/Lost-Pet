package com.example.lostpet.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.lostpet.Constants
import com.example.lostpet.FormActivity
import com.example.lostpet.R
import com.example.lostpet.itemAdapter.AnimalItem
import com.example.lostpet.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.blushine.android.ui.showcase.MaterialShowcaseView
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val mainViewModel by viewModels<HomeViewModel>()

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_main, container, false)
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_fragment_recyclerview?.adapter = groupAdapter
        bindUI()
        val fabView: View? = activity?.findViewById(R.id.main_fab)
        fabView?.setOnClickListener {
            startActivityForResult(Intent(context, FormActivity::class.java), Constants.RESULT_REQUEST_CODE)
        }
    }

    private fun bindUI(){
        mainViewModel.itemList.observe(viewLifecycleOwner, Observer {
            updateRecyclerView(it)
        })
        mainViewModel.getAnimal()
    }

    private fun updateRecyclerView(item: List<AnimalItem>){
        groupAdapter.update(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.RESULT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            MaterialShowcaseView.Builder(requireActivity()).apply {
                setTitleText("Thanks for your help !")
                setContentText("Thanks to you, this animal will surely find its owner!")

                show()
            }
            mainViewModel.getAnimal()
        }
    }
}
