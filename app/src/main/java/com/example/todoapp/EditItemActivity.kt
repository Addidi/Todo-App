package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class EditItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var itemposition = intent.getStringExtra("itemPosition").toString()
        val itemValue = intent.getStringExtra("itemValue").toString()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        val editItem = findViewById<EditText>(R.id.editTask)
        editItem.setText(itemValue)

        findViewById<Button>(R.id.btnEdit).setOnClickListener {
            // Prepare data intent
            val data = Intent()
            // Pass relevant data back as a result
            data.putExtra("editItemPosition", itemposition)
            data.putExtra("newString", editItem.text.toString())
            // Activity finished ok, return the data
            setResult(RESULT_OK, data) // set result code and bundle data for response
            finish() // closes the activity, pass data to parent
        }
    }
}