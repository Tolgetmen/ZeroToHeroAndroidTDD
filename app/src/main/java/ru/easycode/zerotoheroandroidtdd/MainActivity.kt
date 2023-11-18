package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textLine = findViewById<TextView>(R.id.titleTextView)
        val button = findViewById<Button>(R.id.changeButton)
        button.setOnClickListener{
            textLine.text = "I am an Android Developer!"
        }

    }


}