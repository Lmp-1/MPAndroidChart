package com.xxmassdeveloper.mpchartexample.mycustomchart

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.xxmassdeveloper.mpchartexample.R
import java.util.*

class CustomLineChart(context: Context, attrs: AttributeSet?, defStyle: Int) :
        LineChart(context, attrs, defStyle) {

    private val maxPoints = 100
    private val axisMaximum = 1150f
    private val axisMinimum = 800f
    private val startFillFrom = 1010f
    private val fillRange = 110f
    private val itemCount = 100
    private var lastItemAdded = -1

    private val limitLine = LimitLine(0f)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null, 0)

    init {
        initChart()
        initLimitLine()
        initDataSet()
        addItems(itemCount, fillRange, startFillFrom)
    }

    fun addItem() {
        val pointY = (Math.random() * fillRange).toFloat() + startFillFrom
        lastItemAdded++
        data.dataSets[0].addEntry(Entry(lastItemAdded.toFloat(), pointY, null))
        removeExcessItems()
        setLimitLinePosition(pointY)
        data.notifyDataChanged()
        notifyDataSetChanged()
        invalidate()
    }

    fun addItems(count: Int, range: Float, min: Float) {
        var pointY = 0f
        for (i in 0 until count) {
            pointY = (Math.random() * range).toFloat() + min
            data.dataSets[0].addEntry(Entry(i.toFloat(), pointY, null))
        }
        lastItemAdded += count
        setLimitLinePosition(pointY)
        removeExcessItems()
        data.notifyDataChanged()
        notifyDataSetChanged()
        invalidate()
    }

    private fun setLimitLinePosition(newPosition: Float) {
        limitLine.label = newPosition.toString()
        limitLine.limit = newPosition
    }

    private fun initChart() {
        setBackgroundColor(Color.DKGRAY)
        description.isEnabled = false
        legend.isEnabled = false
        setTouchEnabled(false)

        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawLabels(false)

        axisRight.setDrawGridLines(false)
        axisRight.setDrawLabels(false)

        axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        axisLeft.textColor = Color.LTGRAY
        axisLeft.axisMaximum = axisMaximum
        axisLeft.axisMinimum = axisMinimum
    }

    private fun initDataSet() {
        // create a dataset and give it a type
        val set1 = LineDataSet(null, "DataSet 1")
        set1.setDrawIcons(false)
        set1.setDrawCircles(false)
        set1.setDrawCircleHole(false)
        set1.setDrawValues(false)

        // lines color
        set1.color = Color.RED

        // line thickness
        set1.lineWidth = 1f

        // customize legend entry
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f

        // text size of values
        set1.valueTextSize = 9f

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f)

        // set the filled area
        set1.setDrawFilled(true)
        set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> axisLeft.axisMinimum }

        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            val drawable = ContextCompat.getDrawable(context, R.drawable.bg_red_gradient)
            set1.fillDrawable = drawable
        } else {
            set1.fillColor = Color.BLACK
        }
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1) // add the data sets

        // create a data object with the data sets
        data = LineData(dataSets)
    }

    private fun initLimitLine() {
        with(limitLine) {
            lineWidth = 1f
            lineColor = Color.WHITE
            enableDashedLine(12f, 10f, 0f)
            labelPosition = LimitLine.LimitLabelPosition.LEFT_MIDDLE
            textColor = Color.BLACK
            textSize = 10f
            axisLeft.addLimitLine(this)
        }
    }

    private fun removeExcessItems() {
        while (data.dataSets[0].entryCount > maxPoints) {
            data.dataSets[0].removeFirst()
        }
    }
}