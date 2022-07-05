package com.example.todoapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(private val listOfItems : List<String>, val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun OnItemLongClicked(position : Int)
        fun OnItemClicked(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item: String = listOfItems[position]
        // Set item views based on your views and data model
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return  listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val textView : TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnLongClickListener {
                longClickListener.OnItemLongClicked(adapterPosition)
                Log.i("User", "Long press is handled by function : $adapterPosition")
                true
            }
            itemView.setOnClickListener {
                longClickListener.OnItemClicked(adapterPosition)
                Log.i("User", "Item press in RecyclerView : $adapterPosition")
                true
            }
        }
    }

}