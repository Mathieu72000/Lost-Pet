package com.example.lostpet.fragment

import com.example.lostpet.viewmodel.HomeFoundViewModel

class FoundFragment : MainFragment<HomeFoundViewModel>() {

    companion object {
        fun newInstance(): FoundFragment {
            return FoundFragment()
        }
    }

    override fun getViewModelClass(): Class<HomeFoundViewModel> {
        return HomeFoundViewModel::class.java
    }
}