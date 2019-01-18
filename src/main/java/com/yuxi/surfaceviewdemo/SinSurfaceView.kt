package com.yuxi.surfaceviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.sin

class SinSurfaceView :SurfaceView ,SurfaceHolder.Callback,Runnable{
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mIsDrawing=false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mIsDrawing=true
        Thread(this).start()
    }
    private var x = 0
    private var y = 0
    override fun run() {
       while (mIsDrawing){
           draw()
           x+=1
           y=(100* sin(x*2*Math.PI/180f)+400).toInt()
           if(width!=0&&x%width==0){
               mPath.reset()
               mPath.moveTo(x.toFloat()%width,y.toFloat())
           }
           if(width!=0)
                mPath.lineTo(x.toFloat()%width,y.toFloat())
       }
    }

    fun draw(){
        var  mCanvas:Canvas?=null
        try {
            mCanvas=holder.lockCanvas()
            //draw something
            mCanvas?.drawColor(Color.WHITE)
            mCanvas?.drawPath(mPath,mPaint)
        }catch (e:Exception){

        }finally {
            try {
                if(mCanvas!=null)
                mHolder.unlockCanvasAndPost(mCanvas)
            }catch (e:Exception){}

        }
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int)
            : super(context, attributeSet, defStyle) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context, attributeSet: AttributeSet?, defStyle: Int,
        defAttribute: Int
    ) : super(context, attributeSet, defStyle, defAttribute) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init()
    }
    constructor(context: Context):super(context){
        init()
    }
    lateinit var mHolder:SurfaceHolder
    var mIsDrawing =false
    lateinit var mPath:Path
    lateinit var mPaint:Paint
    fun init(){
        mHolder = holder
        mHolder.addCallback(this)
     /*   isFocusable = true
        isFocusableInTouchMode=true*/
        keepScreenOn=true
        mPath = Path()
        mPath.moveTo(0f,400f)
        mPaint = Paint()
        mPaint.style = Paint.Style.STROKE

    }
}