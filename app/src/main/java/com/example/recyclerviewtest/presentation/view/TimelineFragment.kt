package com.example.recyclerviewtest.presentation.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evrencoskun.tableview.TableView
import com.example.recyclerviewtest.BR
import com.example.recyclerviewtest.presentation.adapter.ListItem
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.databinding.FragmentTableTestBinding
import com.example.recyclerviewtest.databinding.FragmentTestBinding
import com.example.recyclerviewtest.databinding.FragmentTimelineBinding
import com.example.recyclerviewtest.di.appComponent
import com.example.recyclerviewtest.presentation.adapter.RecyclerViewAdapter
import com.example.recyclerviewtest.presentation.adapter.TableViewAdapter
import com.example.recyclerviewtest.presentation.table.data.Cell
import com.example.recyclerviewtest.presentation.table.data.ColumnHeader
import com.example.recyclerviewtest.presentation.table.data.RowHeader
import com.example.recyclerviewtest.presentation.viewmodel.TableTestViewModel
import com.example.recyclerviewtest.presentation.viewmodel.TestViewModel
import com.example.recyclerviewtest.presentation.viewmodel.TimelineViewModel
import kotlinx.android.synthetic.main.fragment_test.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TimelineFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentTimelineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appComponent!!.inject(this)

        binding = FragmentTimelineBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProvider(
            this@TimelineFragment,
            viewModelFactory
        ).get(TimelineViewModel::class.java)

        binding.executePendingBindings()
        return binding.root
    }
}