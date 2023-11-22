package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val count = Count.Base(step = 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayout = findViewById<LinearLayout>(R.id.rootLayout)
        val textView = findViewById<TextView>(R.id.countTextView)
        val button = findViewById<Button>(R.id.incrementButton)

        button.setOnClickListener {
            val result = count.increment(textView.text.toString())
            textView.text = result
        }
    }
}

interface Count {
    fun increment(number: String): String
    class Base(private val step: Int) : Count {
        init {
            if (step < 1)
                throw IllegalStateException("step should be positive, but was $step")
        }

        override fun increment(number: String): String {
            val digits = number.toInt()
            val result = digits + step
            return result.toString()
        }
    }
}
