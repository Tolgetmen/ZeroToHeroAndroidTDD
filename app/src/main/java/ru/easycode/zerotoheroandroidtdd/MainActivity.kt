package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.id = R.id.rootLayout

        val textView = TextView(this)
        textView.id = R.id.titleTextView
        textView.text = "Hello World!"
        linearLayout.addView(textView)

        val button = Button(this)
        button.id = R.id.changeButton
        button.text="change"
        linearLayout.addView(button)

        setContentView(linearLayout)


        button.setOnClickListener{
            textView.text = "I am an Android Developer!"
        }

    }


}