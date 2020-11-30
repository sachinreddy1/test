package com.example.recyclerviewtest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import kotlinx.android.synthetic.main.fragment_first.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    lateinit var adapter: RecyclerViewAdapter<ListItem>
    private var isTesting = false
    private var testThread: Thread? = null

//    var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = RecyclerViewAdapter({ R.layout.item_test }, BR.item)
        recyclerView.adapter = adapter
        adapter.itemList = listOf(
            ListItem("0", "0"),
            ListItem("1", "1"),
            ListItem("2", "2"),
            ListItem("3", "3"),
            ListItem("4", "4"),
            ListItem("5", "5"),
            ListItem("6", "6")
        )

        test_button.setOnClickListener {
            requireActivity().runOnUiThread {
                startTestingThread()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun startTestingThread() {
        testThread = object : Thread() {
            override fun run() {
                requireActivity().runOnUiThread {
                    var counter = 0
                    while (isTesting) {
                        counter.toString().let {
                            adapter.itemList = listOf(
                                ListItem("0", it),
                                ListItem("1", it),
                                ListItem("2", it),
                                ListItem("3", it),
                                ListItem("4", it),
                                ListItem("5", it),
                                ListItem("6", it)
                            )
                        }
                        counter += 1
                        sleep(1000000)
                    }
                }
            }
        }

        isTesting = true
        testThread?.start()
    }

    private fun stopTestingThread() {
        isTesting = false
        testThread?.stop()
    }

//    private fun testMethod() {
//        counter.toString().let {
//            adapter.itemList = listOf(
//                ListItem("0", it),
//                ListItem("1", it),
//                ListItem("2", it),
//                ListItem("3", it),
//                ListItem("4", it),
//                ListItem("5", it),
//                ListItem("6", it)
//            )
//        }
//    }
}