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
import com.example.recyclerviewtest.di.appComponent
import com.example.recyclerviewtest.presentation.adapter.RecyclerViewAdapter
import com.example.recyclerviewtest.presentation.adapter.TableViewAdapter
import com.example.recyclerviewtest.presentation.table.data.Cell
import com.example.recyclerviewtest.presentation.table.data.ColumnHeader
import com.example.recyclerviewtest.presentation.table.data.RowHeader
import com.example.recyclerviewtest.presentation.viewmodel.TableTestViewModel
import com.example.recyclerviewtest.presentation.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.fragment_test.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TableTestFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentTableTestBinding
    private lateinit var tableView: TableView
    private lateinit var adapter: TableViewAdapter

    private var thread: Thread? = null
    private var isTesting = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appComponent!!.inject(this)

        binding = FragmentTableTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProvider(
            this@TableTestFragment,
            viewModelFactory
        ).get(TableTestViewModel::class.java)

        // Setting up tableView and adapter
        tableView = TableView(requireContext())
        adapter = TableViewAdapter(
            requireContext()
        )
        tableView.setAdapter(adapter)
        binding.tableView.setAdapter(adapter)

        // FAB
        binding.testButton.setOnClickListener(StartTimer())

        binding.executePendingBindings()
        return binding.root
    }

    private inner class StartTimer : View.OnClickListener {
        override fun onClick(v: View) {
            if (isTesting) {
                isTesting = false
                test_button.setImageResource(R.drawable.ic_baseline_play_arrow_24)

                thread?.join()
                thread = null
            } else {
                isTesting = true
                test_button.setImageResource(R.drawable.ic_baseline_stop_24)

                thread = Thread(Runner())
                thread?.start()
            }
        }
    }

    private inner class Runner : Runnable {
        var i = 0
        override fun run() {
            i = 0
            while (isTesting) {
                i.toString().let { count ->
                    binding.vm?.apply {
                        cells.postValue(
                            listOf(
                                listOf(
                                    Cell(count),
                                    Cell(count),
                                    Cell(count),
                                    Cell(count),
                                    Cell(count),
                                    Cell(count),
                                    Cell(count),
                                    Cell(count)
                                )
                            )
                        )
                        columnHeaders.postValue(
                            listOf(
                                ColumnHeader(count),
                                ColumnHeader(count),
                                ColumnHeader(count),
                                ColumnHeader(count),
                                ColumnHeader(count),
                                ColumnHeader(count),
                                ColumnHeader(count),
                                ColumnHeader(count)
                            )
                        )
                        rowHeaders.postValue(
                            listOf(
                                RowHeader(count)
                            )
                        )
                    }
                }
                i++
                Thread.sleep(100)
            }
        }
    }
}