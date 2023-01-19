package com.berke.expensetrackingapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.fragment_expense_or_income.*


class ExpenseOrIncomeFragment : Fragment() {


    private lateinit var itemList: ArrayList<ExpenseOrIncome>
    private lateinit var itemAdapter : RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_expense_or_income, container, false)



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemList = ArrayList()


        itemAdapter = RecyclerAdapter(itemList)
        addFragmentRexyclerView.layoutManager =LinearLayoutManager (this.context)
        addFragmentRexyclerView.adapter =itemAdapter

        getData()



        floatingButton.setOnClickListener {
            val action = ExpenseOrIncomeFragmentDirections.actionExpenseOrIncomeFragmentToAddFragment()
            Navigation.findNavController(it).navigate(action)

        }
    }

    fun getData (){
        try {
            val database = requireActivity().openOrCreateDatabase("ExpenseJanuary", AppCompatActivity.MODE_PRIVATE,null)

            val cursor = database.rawQuery("SELECT * FROM expenseJanuary",null)
            val expenseorIncomeIx = cursor.getColumnIndex("expenseOrIncome")
            val priceIx = cursor.getColumnIndex("price")
            val hintIx = cursor.getColumnIndex("hint")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val expenseorIncome = cursor.getString(expenseorIncomeIx)
                val id= cursor.getString(idIx)
                val price = cursor.getString(priceIx)
                val hint = cursor.getString(hintIx)
                val items = ExpenseOrIncome(expenseorIncome,price,hint)
                itemList.add(items)

            }

            itemAdapter.notifyDataSetChanged()

            cursor.close()

        }catch (e:Exception){
            e.printStackTrace()
        }


    }
}

