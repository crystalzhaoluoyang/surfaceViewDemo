package com.yuxi.surfaceviewdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onPause() {
        super.onPause()
        surfaceView.surfaceDestroyed(surfaceView.mHolder)
        if(!drawPane.isDrawing){
            drawPane.surfaceCreated(drawPane.holder)
        }
    }

    override fun onResume() {
        super.onResume()
        surfaceView.surfaceCreated(surfaceView.mHolder)
        if(drawPane.isDrawing){
            drawPane.surfaceDestroyed(drawPane.holder)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }
}
