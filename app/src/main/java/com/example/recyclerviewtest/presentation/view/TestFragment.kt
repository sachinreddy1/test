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
    lateinit var testTimer: TextView

    private var handler: Handler? = null
    private var thread: Thread? = null

    private var isTesting = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appComponent!!.inject(this)

        binding = FragmentTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm =
            ViewModelProvider(this@TestFragment, viewModelFactory).get(TestViewModel::class.java)

        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // RecyclerView
        adapter =
            RecyclerViewAdapter(
                { R.layout.item_test },
                BR.item
            )
        recyclerView.adapter = adapter
        adapter.itemList = listOf(
            ListItem("0", "0"),
            ListItem("1", "1"),
            ListItem("2", "2"),
            ListItem("3", "3"),
            ListItem("4", "4"),
            ListItem("5", "5"),
            ListItem("6", "6"),
            ListItem("7", "7")
        )

        handler = Handler()
        testTimer = requireActivity().findViewById(R.id.test_timer)

        test_button.setOnClickListener(StartTimer())
        super.onViewCreated(view, savedInstanceState)
    }

    private inner class StartTimer : View.OnClickListener {
        override fun onClick(v: View) {
            println(binding.vm?.testValue)
//            if (isTesting) {
//                isTesting = false
//                test_button.setImageResource(R.drawable.ic_baseline_play_arrow_24)
//
//                thread?.join()
//                thread = null
//            } else {
//                isTesting = true
//                test_button.setImageResource(R.drawable.ic_baseline_stop_24)
//
//                thread = Thread(Runner())
//                thread?.start()
//            }
            binding.vm?.testValue = "SOMETHING"
        }
    }

    private inner class Runner : Runnable {
        var i = 0
        override fun run() {
            i = 0
            while (isTesting) {
                handler?.post {
                    i.toString().let {
                        testTimer.text = it

                        adapter.itemList = listOf(
                            ListItem(
                                "0",
                                it
                            ),
                            ListItem(
                                "1",
                                it
                            ),
                            ListItem(
                                "2",
                                it
                            ),
                            ListItem(
                                "3",
                                it
                            ),
                            ListItem(
                                "4",
                                it
                            ),
                            ListItem(
                                "5",
                                it
                            ),
                            ListItem(
                                "6",
                                it
                            ),
                            ListItem(
                                "7",
                                it
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