package com.berke.expensetrackingapp2

import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

import kotlinx.android.synthetic.main.fragment_total.*


class TotalFragment : Fragment() {



    private lateinit var priceIncomeList : MutableList<String>
    private lateinit var priceExpenseList : MutableList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        priceIncomeList =  mutableListOf()
        priceExpenseList= mutableListOf()

        // for calculate totalIncome
        val database = requireActivity().openOrCreateDatabase("ExpenseJanuary", AppCompatActivity.MODE_PRIVATE, null)
        val cursor = database.rawQuery("SELECT * FROM expenseJanuary Where expenseOrIncome = 'Income'", null)
        val priceIncomeIx = cursor.getColumnIndex("price")
        while (cursor.moveToNext()) {
            val priceIncome = cursor.getString(priceIncomeIx)
            priceIncomeList.add(priceIncome)
        }
        val sumIncome = priceIncomeList.sumOf {
            it.toDouble()
        }

        //for calculate total Expense
        val database2 =requireActivity().openOrCreateDatabase("ExpenseJanuary",AppCompatActivity.MODE_PRIVATE,null)
        val cursor2 =database2.rawQuery("SELECT * FROM expenseJanuary Where expenseOrIncome = 'Expense'",null)
        val priceExpenseIx = cursor2.getColumnIndex("price")
        while (cursor2.moveToNext()){
            val priceExpense = cursor2.getString(priceExpenseIx)
            priceExpenseList.add(priceExpense)
        }
        val sumExpense = priceExpenseList.sumOf {
            it.toDouble()
        }

        println(sumIncome)
        println(sumExpense)





        // for pieChart

        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(sumExpense.toFloat()))
        pieEntries.add(PieEntry(sumIncome.toFloat()))

        //set pieChart Animation
        pieChart.animateXY(1000,1000)

        //Setup PieChart Entries Color

    val pieDataSet = PieDataSet(pieEntries,"Expense Vs Income")
        pieDataSet.setColors(
            resources.getColor(R.color.purple_500),
            resources.getColor(R.color.teal_200)
        )

        //Setup Pie Data Set in PieData
        val pieData = PieData(pieDataSet)

        //Setup Text in pieChart Center
        pieChart.centerText="Expense Vs Income"
        pieChart.setCenterTextColor(resources.getColor(R.color.black))
        pieChart.setCenterTextSize(15f)


        //Hide the pieChart Enteries Tags
        pieChart.legend.isEnabled=false

        //Hide The description of piechart
        pieChart.description.isEnabled=false

        // show values
        pieData.setDrawValues(true)


        pieChart.data=pieData



        //for textView
        textView2.text="Income = ${sumIncome}"
        textView3.text="Expense = ${sumExpense}"


    }


}




