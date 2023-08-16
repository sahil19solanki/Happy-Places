package com.example.happyplaces

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var fabAddHappyPlace:FloatingActionButton  ?= null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddHappyPlace = findViewById(R.id.fabAddHappyPlace)

        fabAddHappyPlace.setOnClickListener {
            val intent = Intent(this@MainActivity,AddHappyPlace::class.java)
            startActivity(intent)
        }
    }
}