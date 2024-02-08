package com.example.neoticket.Utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.constraintlayout.widget.ConstraintLayout

class ZoomLayout(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f

    init {
        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector?.onTouchEvent(event)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f))
            scaleX = scaleFactor
            scaleY = scaleFactor
            return true
        }
    }
}
