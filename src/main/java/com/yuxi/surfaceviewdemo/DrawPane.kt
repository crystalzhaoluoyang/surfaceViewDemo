package com.yuxi.surfaceviewdemo

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class DrawPane:SurfaceView,SurfaceHolder.Callback,Runnable {
    override fun surfaceDestroyed(holder: SurfaceHolder?) {
      isDrawing = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        isDrawing = true
        Thread(this).start()
    }

    override fun run() {
        val start = System.currentTimeMillis()
       while (isDrawing){
           draw()
       }
        val end = System.currentTimeMillis()
        if(end-start<100){
            try {
                Thread.sleep(100-(end-start))
            }catch (e:InterruptedException){
                e.printStackTrace()
            }
        }
    }
    var isDrawing = true

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }
    fun draw(){
        var mCanvas :Canvas?=null
        try {
            mCanvas = mSurfaceHolder.lockCanvas()
            mCanvas.drawColor(Color.WHITE)
            mCanvas.drawPath(mPath,mPaint)
        }catch (e:Exception){

        }finally {
            if(mCanvas!=null)
                mSurfaceHolder.unlockCanvasAndPost(mCanvas)
        }
    }

    constructor(context:Context):super(context){
        init()
    }
    constructor(context: Context,attributeSet: AttributeSet?):super(context,attributeSet){
        init()
    }
    constructor(context: Context,attributeSet: AttributeSet?,defStyle:Int):super(context,attributeSet,defStyle){
        init()
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context,attributeSet: AttributeSet?,defStyle: Int,defAttri:Int):super
        (context,attributeSet,defStyle,defAttri){
        init()
    }
    lateinit var mPath:android.graphics.Path
    lateinit var mSurfaceHolder:SurfaceHolder
    lateinit var mPaint:Paint
    fun init(){
        mPath = android.graphics.Path()
        mSurfaceHolder = holder
        mSurfaceHolder.addCallback(this)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style=Paint.Style.STROKE
        mPaint.color = Color.BLUE
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn=true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x  = event?.x
        val y  = event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN->
                mPath.moveTo(x!!,y!!)
            MotionEvent.ACTION_MOVE->
                mPath.lineTo(x!!,y!!)
        }
        return true
    }

}