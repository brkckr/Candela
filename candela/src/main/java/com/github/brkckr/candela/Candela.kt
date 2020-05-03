package com.github.brkckr.candela

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class Candela : View
{
    /**
     * colors
     */
    private var colorEmpty = resources.getColor(R.color.color_empty)
    private var colorFill = resources.getColor(R.color.color_fill)
    private var colorSun = resources.getColor(R.color.color_sun)
    private var colorSunlight = resources.getColor(R.color.color_sunlight)

    /**
     * paints
     */
    private val paintFill: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        setColor(colorEmpty)
    }

    private val paintEmpty: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        setColor(colorFill)
    }

    private val paintSun: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        setColor(colorSun)
    }

    private val paintSunlight: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        setColor(colorSunlight)
    }

    /**
     * progress
     */
    private var currentProgress: Int = 50
    private var maxProgress: Int = 255

    /**
     * dimensions
     */
    private var viewHeight: Int = 0
    private var viewWidth: Int = 0
    private var cornerRadius: Int = 10
    private var sunlightStrokeWidth: Int = 0

    /**
     * flags
     */
    private var isSunlightRounded: Boolean = true
    private var isTouchEnabled: Boolean = true

    /**
     * radius of the circle called as sun
     */
    private var sunRadius: Float = 0F

    /**
     * x and y coordinatescenter of sun
     */
    private lateinit var sunCoordinateArray: FloatArray

    /**
     * the length of the sunlight
     */
    private var sunlightLength: Float = 0.toFloat()

    /**
     * the length of the empty space between sun and sunlight;
     */
    private var emptySpaceLength: Float = 0.toFloat()

    /**
     * sizes of the sunlightLength+emptySpaceLength from center of sun
     * the distance between the center of the circle and the beginning of sunlight.
     */
    private var hypotenuse: Int = 0
    private var edge: Int = 0

    private var isMeasured: Boolean = false

    /**
     * listener
     */
    private var progressListener: ProgressListener? = null

    /**
     * initializes style values and objects
     *
     * @param context
     * @param attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Candela)

        colorEmpty = typedArray.getColor(R.styleable.Candela_colorEmpty, resources.getColor(R.color.color_empty))
        colorFill = typedArray.getColor(R.styleable.Candela_colorFill, resources.getColor(R.color.color_fill))
        colorSun = typedArray.getColor(R.styleable.Candela_colorSun, resources.getColor(R.color.color_sun))
        colorSunlight = typedArray.getColor(R.styleable.Candela_colorSunlight, resources.getColor(R.color.color_sunlight))

        maxProgress = typedArray.getInt(R.styleable.Candela_maxProgress, 255)
        currentProgress = typedArray.getInt(R.styleable.Candela_currentProgress, 0)
        cornerRadius = typedArray.getInt(R.styleable.Candela_cornerRadius, 50)

        sunlightStrokeWidth = typedArray.getInt(R.styleable.Candela_sunlightStrokeWidth, 8)
        isTouchEnabled = typedArray.getBoolean(R.styleable.Candela_isTouchEnabled, true)
        isSunlightRounded = typedArray.getBoolean(R.styleable.Candela_isSunlightRounded, true)

        typedArray.recycle()

        // init paints
        paintEmpty.setColor(colorEmpty)

        paintFill.setColor(colorFill)

        paintSun.setColor(colorSun)

        paintSunlight.setColor(colorSunlight)
        if (isSunlightRounded) paintSunlight.setStrokeCap(Paint.Cap.ROUND)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        if (!isMeasured) {
            sunRadius = calculateSunRadius()
            sunCoordinateArray = calculateSunCoordinates(sunRadius)
            sunlightLength = calculateSunlightLength(sunRadius)
            emptySpaceLength = calculateEmptySpaceLength(sunRadius)

            hypotenuse = (sunRadius + emptySpaceLength).toInt()

            // measure of one of the edges of an isosceles triangle
            // formula -> Math.pow(h,2) = Math.pow(edge1, 2) + Math.pow(edge2, 2);
            // so it is isosceles triangle, means edge1's length == edge2's length
            // then formula becomes -> Math.pow(h,2) = 2*Math.pow(edge, 2);
            edge = Math.sqrt((hypotenuse * hypotenuse / 2).toDouble()).toInt()

            isMeasured = true
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)

        //calculates horizontal position in pixel
        val positionProgress = (viewHeight * (this.maxProgress - this.currentProgress) / this.maxProgress).toFloat()

        //draws empty progress
        drawEmptyProgress(canvas)

        //draws fill progress
        drawFillProgress(canvas, positionProgress)

        //draws sun
        drawSun(canvas, paintSun, sunRadius, this.currentProgress)

        //draws 8 sunlight
        drawAllSunlight(canvas, hypotenuse, edge)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        val y = event.getY()

        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> if (isTouchEnabled) {
                this.currentProgress = calculateCurrentProgress(y)
                checkCurrentProgress()
                progressListener?.onProgressChange(this.currentProgress)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                this.currentProgress =  calculateCurrentProgress(y)
                checkCurrentProgress()
                progressListener?.onProgressChange(this.currentProgress)
                invalidate()
            }
        }

        return true
    }

    private fun drawEmptyProgress(canvas: Canvas)
    {
        val path = Path()
        path.addRoundRect(
            RectF(0F, 0F, viewWidth.toFloat(), viewHeight.toFloat()),
            cornerRadius.toFloat(),
            cornerRadius.toFloat(),
            Path.Direction.CCW
        )
        canvas.clipPath(path, Region.Op.INTERSECT)
        canvas.drawRect(0F, 0F, viewWidth.toFloat(), viewHeight.toFloat(), paintEmpty)
    }

    private fun drawFillProgress(canvas: Canvas, positionProgress: Float)
    {
        canvas.drawRect(0F, positionProgress, viewWidth.toFloat(), viewHeight.toFloat(), paintFill)
    }

    /**
     * draws a sun
     *
     * @param canvas          : our strong canvas
     * @param paint           : paint for sun
     * @param radius          : radius of circle(sun)
     * @param currentProgress : current progress of the Candela.
     */
    private fun drawSun(canvas: Canvas, paint: Paint, radius: Float, currentProgress: Int) {
        //I want the size of the sun to change according to currentProgress.
        //the smallest form of the sun will be drawn when currentProgress is 0.
        //when currentProgress is 0, I want to draw it with the half radius
        //so when currentProgress is 0, the sun will still stay on the screen.
        var minRadius = radius / 2
        var maxRadius = radius
        var difference = maxRadius - minRadius

        // changes radius according to currentProgress
       var abc = minRadius + difference * currentProgress / this.maxProgress

        // draws circle to the already calculated coordinates
        canvas.drawCircle(sunCoordinateArray[0], sunCoordinateArray[1], abc, paint)
    }

    /**
     * draws all the sunlight around the sun
     * performs 8 drawing operations at 45(360/8) degree intervals.
     */
    private fun drawAllSunlight(canvas: Canvas, hypotenuse: Int, edge: Int)
    {
        // starts to draw 8 sunlight on the screen according to the clockwise direction.
        drawSunlight(canvas, paintSunlight, 0.0, 0, -hypotenuse, this.currentProgress)
        drawSunlight(canvas, paintSunlight, 1.0, edge, -edge, this.currentProgress)
        drawSunlight(canvas, paintSunlight, 2.0, hypotenuse, 0, this.currentProgress)
        drawSunlight(canvas, paintSunlight, 3.0, edge, edge, this.currentProgress)
        drawSunlight(canvas, paintSunlight, 4.0, 0, +hypotenuse, this.currentProgress)
        drawSunlight(canvas, paintSunlight, 5.0, -edge, edge, this.currentProgress)
        drawSunlight(canvas, paintSunlight, 6.0, -hypotenuse, 0, this.currentProgress)
        drawSunlight(canvas, paintSunlight, 7.0, -edge, -edge, this.currentProgress)
    }

    /**
     * draws a simple line according to the angle
     *
     * @param canvas ´        : our beloved canvas
     * @param paint           : sunlightPaint already init
     * @param location        : from 0-7, according to clockwise
     * @param dx              : The difference between x value of the center of the sun and x value of the starting point of sunlight
     * @param dy              : The difference between y value of the center of the sun and y value of the starting point of sunlight
     * @param currentProgress : current progress of the view.
     */
    private fun drawSunlight(
        canvas: Canvas,
        paint: Paint,
        location: Double,
        dx: Int,
        dy: Int,
        currentProgress: Int
    ) {
        // gets the angle
        val angle = Math.PI * location / 4 - Math.PI / 2

        // sets the stroke
        paint.setStrokeWidth(sunlightStrokeWidth.toFloat())

        // calculates start points
        val startX = sunCoordinateArray[0] + dx
        val startY = sunCoordinateArray[1] + dy

        // // calculates end points
        val stopX = (sunCoordinateArray[0] + (Math.cos(angle) * sunlightLength)).toFloat()
        val stopY = (sunCoordinateArray[1] + (Math.sin(angle) * sunlightLength)).toFloat()

        if (isSunlightRounded) paint.setStrokeCap(Paint.Cap.ROUND)

        // draws line
        canvas.drawLine(
            startX,
            startY,
            startX + (stopX - startX) * currentProgress / this.maxProgress,
            startY + (stopY - startY) * currentProgress / this.maxProgress,
            paint
        )
    }

    /**
     * calculates current progress
     */
    private fun calculateCurrentProgress(y: Float): Int
    {
        return  (this.maxProgress - (this.maxProgress * y / viewHeight).toInt())
    }

    private fun checkCurrentProgress()
    {
        if (this.currentProgress < 0) currentProgress = 0
        if (this.currentProgress > this.maxProgress) currentProgress = this.maxProgress
    }

    fun setProgressListener(progressListener: ProgressListener) {
        this.progressListener = progressListener
    }

    fun setCurrentProgress(currentProgress: Int) {
        this.currentProgress = currentProgress
    }

    fun getCurrentProgress() : Int {
       return this.currentProgress
    }

    fun setMaxProgress(maxProgress: Int)
    {
        if(maxProgress< currentProgress) this.maxProgress  = currentProgress

        this.maxProgress = maxProgress
    }

    fun getMaxProgress() : Int {
        return this.maxProgress
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = cornerRadius
        postInvalidate()
    }

    fun setSunlightStrokeWidth(sunlightStrokeWidth: Int) {
        this.sunlightLength = sunlightStrokeWidth.toFloat()
        postInvalidate()
    }

    /**
     * gets the radius of sun
     *
     * @return
     */
    private fun calculateSunRadius(): Float {
        return (viewWidth / 10 * 2).toFloat()
    }

    /**
     * gets the coordinates of the center point of the sun.
     *
     * @return coordinates array as float
     */
    private fun calculateSunCoordinates(sunRadius: Float): FloatArray {
        val coordinateArray = FloatArray(2)
        coordinateArray[0] = (viewWidth / 2).toFloat()
        coordinateArray[1] = viewHeight * 9 / 10 - sunRadius
        return coordinateArray
    }

    /**
     * gets the length of the sunlight.
     *
     * @param sunRadius : radius of sun.
     * @return : length of the sunlight.
     */
    private fun calculateSunlightLength(sunRadius: Float): Float {
        return sunRadius * 3 / 2
    }

    private fun calculateEmptySpaceLength(sunRadius: Float): Float {
        return sunRadius * 1 / 4
    }
}