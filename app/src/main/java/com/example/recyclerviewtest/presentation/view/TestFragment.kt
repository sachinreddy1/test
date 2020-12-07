package com.example.recyclerviewtest.presentation.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewtest.BR
import com.example.recyclerviewtest.presentation.adapter.ListItem
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.databinding.FragmentTestBinding
import com.example.recyclerviewtest.di.appComponent
import com.example.recyclerviewtest.presentation.adapter.RecyclerViewAdapter
import com.example.recyclerviewtest.presentation.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.fragment_test.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TestFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentTestBinding
    lateinit var adapter: RecyclerViewAdapter<ListItem>

    private var thread: Thread? = null

    private var isTesting = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appComponent!!.inject(this)

        binding = FragmentTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProvider(this@TestFragment, viewModelFactory).get(TestViewModel::class.java)

        // RecyclerView
        adapter =
            RecyclerViewAdapter(
                { R.layout.item_test },
                BR.item
            )
        binding.recyclerView.adapter = adapter
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
                    binding.vm?.testValue?.let {
                        it.value = count
                        it.list = listOf(
                            ListItem("0", count),
                            ListItem("1", count),
                            ListItem("2", count),
                            ListItem("3", count),
                            ListItem("4", count),
                            ListItem("5", count),
                            ListItem("6", count),
                            ListItem("7", count)
                        )
                    }
                }
                i++
                Thread.sleep(100)
            }
        }
    }
}