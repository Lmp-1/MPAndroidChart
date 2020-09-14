package com.xxmassdeveloper.mpchartexample.mycustomchart

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.xxmassdeveloper.mpchartexample.R

class CustomLineChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_line_chart)
        val chart = findViewById<CustomLineChart>(R.id.chart)
        val btnAddItem = findViewById<Button>(R.id.btnAddItem)
        btnAddItem.setOnClickListener { chart.addItem() }
    }
}