package com.berke.expensetrackingapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berke.expensetrackingapp2.databinding.RecylcerviewRowBinding
import kotlinx.android.synthetic.main.recylcerview_row.view.*

class RecyclerAdapter ( val itemList : ArrayList<ExpenseOrIncome> ) : RecyclerView.Adapter<RecyclerAdapter.ItemHolder>() {

    class ItemHolder( var view: View) : RecyclerView.ViewHolder (view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recylcerview_row,parent,false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.view.expenseTextView.text = itemList.get(position).expenseOrIncome
        holder.view.priceTextView.text = itemList.get(position).prive
        holder.view.hintTextView.text = itemList.get(position).hint

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}