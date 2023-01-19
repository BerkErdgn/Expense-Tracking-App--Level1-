package com.berke.expensetrackingapp2

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.berke.expensetrackingapp2.databinding.FragmentAddBinding
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var dataBase : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddBinding.inflate(inflater,container,false)

        //for AutoCompleteTextView
        val expenseorIncome = resources.getStringArray(R.array.exponseSelect)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,expenseorIncome)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener {
            val expenseOrIncome = autoCompleteTextView.text.toString()
            val price = priceTextView.text.toString()
            val hint = hintTextView.text.toString()


            try{
                val dataBase = requireActivity().openOrCreateDatabase("ExpenseJanuary", AppCompatActivity.MODE_PRIVATE,null)
                dataBase.execSQL("CREATE TABLE IF NOT EXISTS expenseJanuary ( id INTEGER PRIMARY KEY,expenseOrIncome VARCHAR, price VARCHAR, hint VARCHAR)")
                val sqlString = "INSERT INTO expenseJanuary (expenseOrIncome,price,hint) VALUES ( ?,?,?)"
                val statement = dataBase.compileStatement(sqlString)
                statement.bindString(1,expenseOrIncome)
                statement.bindString(2,price)
                statement.bindString(3,hint)
                statement.execute()


            }catch (e:Exception){
                e.printStackTrace()
            }




            val action =AddFragmentDirections.actionAddFragmentToExpenseOrIncomeFragment()
            Navigation.findNavController(it).navigate(action)


        }
    }

}