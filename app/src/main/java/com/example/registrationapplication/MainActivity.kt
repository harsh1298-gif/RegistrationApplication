package com.example.registrationapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper
    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etPhone: EditText
    lateinit var radioGroup: RadioGroup
    lateinit var listView: ListView
    lateinit var registerButton: Button

    var selectedCourse = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)
        FindViewBYID()
        Body()
    }

    private fun FindViewBYID() {
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etPhone = findViewById(R.id.etPhone)
        radioGroup = findViewById(R.id.radioGroup)
        listView = findViewById(R.id.list_items)
        registerButton = findViewById(R.id.btn_register)
    }

    private fun Body() {
        val items = listOf("BCA", "MCA", "MSC IT", "MBA")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items
        )
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedCourse = items[position]
            Log.d(TAG, "body: $selectedCourse")
            Toast.makeText(this, "Selected: $selectedCourse", Toast.LENGTH_SHORT).show()
        }

        registerButton.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val phone = etPhone.text.toString()

            val selectedGenderId = radioGroup.checkedRadioButtonId
            val gender = when (selectedGenderId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                else -> ""
            }

            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || gender.isEmpty() || selectedCourse.isEmpty()) {
                Toast.makeText(this, "Please fill all fields and select a course", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fullName = "$firstName $lastName"
            val result = db.insertData(fullName, phone, gender, selectedCourse)

            if (result)
                Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
        }
    }
}