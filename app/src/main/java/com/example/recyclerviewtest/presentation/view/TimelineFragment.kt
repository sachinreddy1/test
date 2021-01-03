package com.example.recyclerviewtest.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewtest.BR
import com.example.recyclerviewtest.presentation.adapter.ListItem
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.databinding.FragmentTimelineBinding
import com.example.recyclerviewtest.di.appComponent
import com.example.recyclerviewtest.presentation.adapter.BottomRecyclerViewAdapter
import com.example.recyclerviewtest.presentation.adapter.RecyclerViewAdapter
import com.example.recyclerviewtest.presentation.viewmodel.TimelineViewModel
import com.example.timelineview.RecyclerView
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TimelineFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentTimelineBinding
    lateinit var adapter: RecyclerViewAdapter<ListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appComponent!!.inject(this)

        binding = FragmentTimelineBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProvider(this@TimelineFragment, viewModelFactory).get(TimelineViewModel::class.java)

        binding.topbar.adapter = RecyclerViewAdapter<ListItem>(
            { R.layout.item_timeline_test },
            BR.item
        )
        binding.bottombar.adapter = BottomRecyclerViewAdapter<ListItem>(
            { R.layout.item_timeline_test },
            BR.item
        )

        binding.bottombar.setTopRecyclerView(binding.topbar)

        binding.executePendingBindings()
        return binding.root
    }
}