package com.example.todoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun OnItemLongClicked(position: Int) {
                //remove the item from the list
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()
            }
            override fun OnItemClicked(position: Int) {
                val i = Intent(this@MainActivity, EditItemActivity::class.java)
                i.putExtra("itemPosition",position.toString())
                i.putExtra("itemValue", listOfTasks[position])
                editActivityResultLauncher.launch(i)
            }
        }
        loadItems()
        // Lookup the recyclerview in activity layout
        val rvItem = findViewById<View>(R.id.recyclerView) as RecyclerView
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        rvItem.adapter = adapter
        // Set layout manager to position the items
        rvItem.layoutManager = LinearLayoutManager(this)

        //when user click on the add button
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val TaskValue = findViewById<EditText>(R.id.addTaskField)
            listOfTasks.add(TaskValue.text.toString())
            adapter.notifyItemInserted(listOfTasks.size - 1)
            TaskValue.setText("")
            saveItems()
        }
    }

    var editActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // If the user comes back to this activity from EditActivity
        // with no error or cancellation
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            // Get the data passed from EditActivity
            if (data != null) {
                val position = data.extras!!.getString("editItemPosition")
                val value = data.extras!!.getString("newString")
                if (value != null) {
                    if (position != null) {
                        listOfTasks[position.toInt()] = value
                        adapter.notifyDataSetChanged()
                        saveItems()
                    }
                }
            }
        }
    }

    //Save the data that the user has inputted in a file
    //Save data By writing and reading a file
    //Get the file we need
    private fun getDataFile() : File {
        return File(filesDir,"db.txt")
    }

    //Load item the file Line By Line
    private fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException : IOException) {
            ioException.printStackTrace()
        }
    }

    //Save item by writing them to the file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException : IOException){
            ioException.printStackTrace()
        }
    }
}