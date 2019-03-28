package com.nollpointer.firstquantumapp.View

import android.content.Context
import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import com.nollpointer.firstquantumapp.R
import kotlin.math.pow

class InfoCard(context: Context) : CardView(context) {

    private val checkBoxContainerStart: LinearLayout
    private val checkBoxContainerEnd: LinearLayout

    private val titleStart: TextView
    private val titleEnd: TextView
    private val subtitleStart: TextView
    private val subtitleEnd: TextView

    lateinit var checkBoxListStart: MutableList<CheckBox>
    lateinit var checkBoxListEnd: MutableList<CheckBox>

    var numberStart = 0
    var numberEnd = 0

    val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        updateNumberFromCheckBoxes(checkBoxListStart.contains(buttonView))
    }

    init {
        val view = View.inflate(context, R.layout.cell, null)
        addView(view)

        checkBoxContainerStart = view.findViewById(R.id.checkboxContainerStart)
        checkBoxContainerEnd = view.findViewById(R.id.checkboxContainerEnd)

        titleStart = view.findViewById(R.id.titleStart)
        titleEnd = view.findViewById(R.id.titleEnd)
        subtitleStart = view.findViewById(R.id.subtitleStart)
        subtitleEnd = view.findViewById(R.id.subtitleEnd)
    }

    fun setData(data: InfoData) {
        titleStart.text = data.titleStart
        titleEnd.text = data.titleEnd
        subtitleStart.text = "Dec: ${data.numberStart}"
        subtitleEnd.text = "Dec: ${data.numberEnd}"

        checkBoxListStart = mutableListOf()
        for (i in 0..10) {
            val checkBox = CheckBox(context)
            checkBox.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1.0f
            )
            checkBox.text = "Bit$i"
            checkBox.setOnCheckedChangeListener(listener)

            checkBoxContainerStart.addView(checkBox)
            checkBoxListStart.add(checkBox)
        }

        checkBoxListEnd = mutableListOf()
        for (i in 0..10) {
            val checkBox = CheckBox(context)
            checkBox.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1.0f
            )
            checkBox.text = "Bit$i"
            checkBox.setOnCheckedChangeListener(listener)

            checkBoxContainerEnd.addView(checkBox)
            checkBoxListEnd.add(checkBox)
        }
    }

    private fun updateNumberFromCheckBoxes(isStart: Boolean) {
        var number = 0
        if (isStart) {
            checkBoxListStart.forEachIndexed { index, checkBox ->
                if (checkBox.isChecked)
                    number += 2.toDouble().pow(index).toInt()
            }

            subtitleStart.text = "Dec: $number"
            numberStart = number
        } else {
            checkBoxListEnd.forEachIndexed { index, checkBox ->
                if (checkBox.isChecked)
                    number += 2.toDouble().pow(index).toInt()
            }

            subtitleEnd.text = "Dec: $number"
            numberEnd = number
        }
    }

    private fun updateCheckBoxesFromNumber() {
        var number = numberStart
        Log.e("TAG","$number")
        for (index in checkBoxListStart.size-1 downTo 0 step 1) {
            val value = 2.0.pow(index).toInt()
            if (number >= value) {
                checkBoxListStart[index].isChecked = true
                number -= value;
            }else
                checkBoxListStart[index].isChecked = false

            Log.e("TAG","$number")
        }
        subtitleStart.text = "Dec: $numberStart"

        number = numberEnd
        Log.e("TAG","$number")
        for (index in checkBoxListEnd.size-1 downTo 0) {
            val value = 2.0.pow(index).toInt()
            if (number >= value) {
                checkBoxListEnd[index].isChecked = true
                number -= value;
            }else
                checkBoxListEnd[index].isChecked = false

            Log.e("TAG","$number")
        }
        subtitleEnd.text = "Dec: $numberEnd"
    }

    fun getValues() = Pair(numberStart, numberEnd)

    fun setValues(values: Pair<Int,Int>){
        numberStart = values.first
        numberEnd = values.second

        updateCheckBoxesFromNumber()
    }

    data class InfoData(val titleStart: String, val titleEnd: String, val numberStart: Int, val numberEnd: Int)

}