package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var textView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textView = findViewById(R.id.titleTextView)
        progressBar = findViewById(R.id.progressBar)
        button = findViewById(R.id.actionButton)

        button.setOnClickListener {
            button.isEnabled = false
            progressBar.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                textView.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                button.isEnabled = true
            },3000)




        }


    }


}