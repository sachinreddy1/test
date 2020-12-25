package com.example.recyclerviewtest.presentation.listener

import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

class TimelineListener(
    val topbar: RecyclerView,
    val bottomBar: RecyclerView
) : RecyclerView.OnScrollListener(), OnItemTouchListener {

    private var mIsMoved = false
    private var mXPosition = 0

    private var mCurrentRVTouched: RecyclerView? = null
    private var mLastTouchedRecyclerView: RecyclerView? = null

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (mCurrentRVTouched != null && rv !== mCurrentRVTouched)
            return true

        if (e.action == MotionEvent.ACTION_DOWN) {
            mCurrentRVTouched = rv
            if (rv.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mLastTouchedRecyclerView != null && rv !== mLastTouchedRecyclerView) {
                    if (mLastTouchedRecyclerView === topbar) {
                        topbar.removeOnScrollListener(this)
                        topbar.stopScroll()
                    } else {
                        bottomBar.removeOnScrollListener(this)
                        bottomBar.stopScroll()
                    }
                }
                mXPosition = rv.scrollX
                rv.addOnScrollListener(this)
            }
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            mCurrentRVTouched = rv
            mIsMoved = true
        } else if (e.action == MotionEvent.ACTION_UP) {
            mCurrentRVTouched = null
            val nScrollX = rv.scrollX

            if (mXPosition == nScrollX && !mIsMoved)
                rv.removeOnScrollListener(this)

            mLastTouchedRecyclerView = rv
        } else if (e.action == MotionEvent.ACTION_CANCEL) {
            rv.removeOnScrollListener(this)
            mIsMoved = false
            mLastTouchedRecyclerView = rv
            mCurrentRVTouched = null
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView === topbar) {
            super.onScrolled(recyclerView, dx, dy)
            bottomBar.scrollBy(dx, 0)
        } else {
            super.onScrolled(recyclerView, dx, dy)
            topbar.scrollBy(dx, 0)
        }
    }

    override fun onScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int
    ) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView.removeOnScrollListener(this)
            mIsMoved = false
        }
    }
}