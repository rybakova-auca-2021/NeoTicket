package com.example.neoticket.Utils

import android.view.MotionEvent
import android.view.View

class SwipeTouchListener(private val view: View) : View.OnTouchListener {

    private var downX: Float = 0.toFloat()
    private var downY: Float = 0.toFloat()

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - downX
                val dy = event.y - downY

                view.translationX += dx
                view.translationY += dy

                downX = event.x
                downY = event.y

                return true
            }
        }
        return false
    }
}
