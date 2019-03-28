package com.nollpointer.firstquantumapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.nollpointer.firstquantumapp.View.InfoCard
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager

    lateinit var initialValuesInfoCard: InfoCard
    lateinit var intermediateValuesInfoCard: InfoCard
    lateinit var resultValuesInfoCard: InfoCard


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 23)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        viewPager = findViewById(R.id.viewPager)

        val adapter = InfoCardAdapter(this, getInfoCards())
        viewPager.adapter = adapter

        val calculateButton = findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener {
            calculate()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_clear -> clearCards()
                R.id.menu_info -> showInfoDialog()
            }
            true
        }
    }

    private fun clearCards() {
        val clearValue = Pair(0, 0)
        initialValuesInfoCard.setValues(clearValue)
        intermediateValuesInfoCard.setValues(clearValue)
        resultValuesInfoCard.setValues(clearValue)
    }

    private fun calculate() {
        val values = initialValuesInfoCard.getValues()

        val intermediateNumberStart = values.first.toDouble().pow(3).toInt()
        val intermediateNumberEnd = values.second.toDouble().pow(3).toInt()

        intermediateValuesInfoCard.setValues(Pair(intermediateNumberStart, intermediateNumberEnd))

        val resultNumberStart = intermediateNumberStart * intermediateNumberEnd
        val resultNumberEnd = intermediateNumberStart + resultNumberStart - intermediateNumberEnd

        resultValuesInfoCard.setValues(Pair(resultNumberStart, resultNumberEnd))

    }


    private fun getInfoCards(): List<InfoCard> {
        initialValuesInfoCard = InfoCard(this)
        initialValuesInfoCard.setData(InfoCard.InfoData("A =", "B =", 0, 0))

        intermediateValuesInfoCard = InfoCard(this)
        intermediateValuesInfoCard.setData(InfoCard.InfoData("A^3 =", "B^3 =", 0, 0))

        resultValuesInfoCard = InfoCard(this)
        resultValuesInfoCard.setData(InfoCard.InfoData("(A*B)^3 =", "C =", 0, 0))

        val list = mutableListOf(initialValuesInfoCard, intermediateValuesInfoCard, resultValuesInfoCard)

        return list;
    }

    private fun showInfoDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Информация")
        val info = "Сделал: <u>Онанов Алексей</u><br>" +
                "Группа: <u>ПМИ-б-о-17-1</u>"
        builder.setMessage(Html.fromHtml(info))
        builder.setPositiveButton("Ок"){ dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }


    class InfoCardAdapter(val context: Context, val infoCardsList: List<InfoCard>) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            container.addView(infoCardsList[position])
            return infoCardsList[position]

        }

        override fun isViewFromObject(p0: View, p1: Any) = p0 == p1

        override fun getCount() = infoCardsList.size

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}
