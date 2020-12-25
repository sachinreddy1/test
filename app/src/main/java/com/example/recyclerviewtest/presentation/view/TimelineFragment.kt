package com.example.recyclerviewtest.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.BR
import com.example.recyclerviewtest.presentation.adapter.ListItem
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.databinding.FragmentTimelineBinding
import com.example.recyclerviewtest.di.appComponent
import com.example.recyclerviewtest.presentation.adapter.RecyclerViewAdapter
import com.example.recyclerviewtest.presentation.listener.TimelineListener
import com.example.recyclerviewtest.presentation.viewmodel.TimelineViewModel
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
        binding.bottombar.adapter = RecyclerViewAdapter<ListItem>(
            { R.layout.item_timeline_test },
            BR.item
        )

        binding.topbar.addOnItemTouchListener(TimelineListener(binding.topbar, binding.bottombar))
        binding.bottombar.addOnItemTouchListener(TimelineListener(binding.topbar, binding.bottombar))

        binding.executePendingBindings()
        return binding.root
    }
}