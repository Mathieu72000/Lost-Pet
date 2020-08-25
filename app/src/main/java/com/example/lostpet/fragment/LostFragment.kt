package com.example.lostpet.fragment

import com.example.lostpet.viewmodel.HomeLostViewModel

class LostFragment : MainFragment<HomeLostViewModel>() {

    companion object {
        fun newInstance(): LostFragment {
            return LostFragment()
        }
    }

    override fun getViewModelClass(): Class<HomeLostViewModel> {
        return HomeLostViewModel::class.java
    }
}