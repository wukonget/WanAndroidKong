package com.pengc.wanandroidkong.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.utils.CommonUtils

class LoadingView : View {

    private var contentWidth: Float = 0f
    private var loadingTextWidth: Float= 0f
    private var textBottom: Float? = 0f
    private var textTop: Float? = 0f
    private var loadingTextCenterY: Int = 0
    private var loadingTextCentertX: Int = 0
    private var loadingTextPosition: Int = 2
    private var loadingTextMarginTop: Float = CommonUtils.dp2px(8f).toFloat()
    private var loadingTextColor: Int = context.resources.getColor(android.R.color.darker_gray)
    private var loadingTextSize: Float = CommonUtils.dp2px(12f).toFloat()
    private var loadingText: String? = "加载中..."
    private var circleColor: Int = context.resources.getColor(R.color.colorPrimary)
    private var repeatDuration: Int = 500
    private var circleMinR: Float = CommonUtils.dp2px(15f).toFloat()
    private var circleMaxR: Float = CommonUtils.dp2px(25f).toFloat()
    private var centerTextColor: Int = context.resources.getColor(R.color.colorAccent)
    private var centerText: String? = "K"
    private var textM: Paint.FontMetrics? = null
    private var centerY: Int = 0
    private var centerX: Int = 0
    private var centerTextSize: Float = CommonUtils.dp2px(20f).toFloat()
    private lateinit var mPaintText: Paint
    private lateinit var mPaintLoadingText: Paint
    private lateinit var mPaint1: Paint
    private var maxPadding = 0
    private var animatValue: Int = 0
    private var strokeWidth = CommonUtils.dp2px(1f).toFloat()
    private var circle1R = circleMaxR
    private var circle2R = circleMinR

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initSetting(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
//        initPaint()
    }

    private fun initSetting(context: Context?, attrs: AttributeSet?) {
        val t: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        centerText = t?.getString(R.styleable.LoadingView_LCenterText) ?: "K"
        centerTextSize =
            t?.getDimensionPixelSize(R.styleable.LoadingView_LCenterTextSize, CommonUtils.dp2px(20f))!!.toFloat()
        centerTextColor =
            t.getColor(R.styleable.LoadingView_LCenterTextColor, context.resources.getColor(R.color.colorAccent))
        circleMaxR = t.getDimensionPixelSize(R.styleable.LoadingView_LCircleMaxR, CommonUtils.dp2px(20f)).toFloat()
        circleMinR = t.getDimensionPixelSize(R.styleable.LoadingView_LCircleMinR, CommonUtils.dp2px(15f)).toFloat()
        repeatDuration = t.getInt(R.styleable.LoadingView_LRepeatDuration, 1000)/2
        circleColor = t.getColor(R.styleable.LoadingView_LCircleColor, context.resources.getColor(R.color.colorPrimary))
        loadingText = t.getString(R.styleable.LoadingView_LLoadingText) ?: "加载中..."
        loadingTextSize =
            t.getDimensionPixelSize(R.styleable.LoadingView_LLoadingTextSize, CommonUtils.dp2px(12f)).toFloat()
        loadingTextColor =
            t.getColor(R.styleable.LoadingView_LLoadingTextColor, context.resources.getColor(android.R.color.darker_gray))
        loadingTextMarginTop =
            t.getDimensionPixelSize(R.styleable.LoadingView_LLoadingTextMarginTop, CommonUtils.dp2px(8f)).toFloat()
        loadingTextPosition = t.getInt(R.styleable.LoadingView_LLoadingTextPosition, 2)
        strokeWidth = t.getDimension(R.styleable.LoadingView_LStorkeWidth, CommonUtils.dp2px(1f).toFloat())
        initPaint()
    }

    private fun initPaint() {

        mPaint1 = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint1.style = Paint.Style.STROKE
        mPaint1.strokeWidth = strokeWidth
        mPaint1.color = circleColor

        mPaintText = Paint(mPaint1)
        mPaintText.color = centerTextColor
        mPaintText.textSize = centerTextSize
        mPaintText.strokeWidth = 0f
        mPaintText.textAlign = Paint.Align.CENTER
        mPaintText.isFakeBoldText = true

        textM = mPaintText.fontMetrics
        textTop = textM?.top
        textBottom = textM?.bottom

        mPaintLoadingText = Paint(mPaintText)
        mPaintLoadingText.textSize = loadingTextSize
        mPaintLoadingText.color = loadingTextColor

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthResult = 0
        //view根据xml中layout_width和layout_height测量出对应的宽度和高度值，
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        when (widthSpecMode) {
            MeasureSpec.UNSPECIFIED -> widthResult = widthSpecSize
            MeasureSpec.AT_MOST//wrap_content时候
            -> widthResult = getContentWidth()
            MeasureSpec.EXACTLY -> widthResult = widthSpecSize
        }

        var heightResult = 0
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        when (heightSpecMode) {
            MeasureSpec.UNSPECIFIED -> heightResult = heightSpecSize
            MeasureSpec.AT_MOST//wrap_content时候
            -> heightResult = getContentHeight()
            MeasureSpec.EXACTLY -> heightResult = heightSpecSize
        }
//        val min = Math.min(widthResult, heightResult)//暂定是圆形的，先取最小的吧
        maxPadding = Math.max(paddingLeft, paddingTop)
//        centerX = (circleMaxR + paddingLeft + strokeWidth / 2).toInt()
//        centerY = (circleMaxR + paddingTop + strokeWidth / 2).toInt()

        centerX = widthResult/2
        centerY = heightResult/2

        loadingTextCentertX = centerX
        loadingTextCenterY = (paddingTop+strokeWidth+circleMaxR*2+loadingTextMarginTop).toInt()
        setMeasuredDimension(widthResult, heightResult)

    }

    private fun getContentHeight(): Int {
        return (circleMaxR * 2 + paddingTop + paddingBottom + loadingTextMarginTop + strokeWidth + loadingTextSize).toInt()
    }

    private fun getContentWidth(): Int {
        loadingTextWidth = mPaintLoadingText.measureText(loadingText)*1.2.toFloat() + paddingLeft + paddingRight
        contentWidth = circleMaxR * 2 + paddingLeft + paddingRight+strokeWidth
        return (Math.max(loadingTextWidth,contentWidth)).toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawCenterText(it)
            drawCircle1(it)
            drawLoadingText(it)
        }

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnim()
    }

    private fun startAnim() {
        val va = ValueAnimator.ofInt(0, (circleMaxR - circleMinR).toInt())
        va.repeatMode = ValueAnimator.REVERSE
        va.repeatCount = -1
        va.duration = repeatDuration.toLong()
        va.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        va.addUpdateListener {
            animatValue = it.animatedValue as Int
            invalidate()
        }
        va.start()
    }

    private fun drawCircle1(canvas: Canvas) {
        circle1R = circleMaxR - animatValue
        canvas.drawCircle(centerX.toFloat(), paddingTop+strokeWidth/2+circleMaxR, circle1R, mPaint1)
    }

    private fun drawCenterText(canvas: Canvas) {
        canvas.drawText(centerText!!, centerX.toFloat(), paddingTop+strokeWidth/2+circleMaxR - textTop!!.div(2) - textBottom!!.div(2), mPaintText)
    }

    private fun drawLoadingText(canvas: Canvas) {
        canvas.drawText(loadingText!!, loadingTextCentertX.toFloat(), loadingTextCenterY - textTop!!.div(2) - textBottom!!.div(2), mPaintLoadingText)
    }
}