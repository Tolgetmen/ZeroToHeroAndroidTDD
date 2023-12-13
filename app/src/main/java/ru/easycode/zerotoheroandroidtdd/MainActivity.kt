package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val count = Count.Base(2, 4, 0)


    private lateinit var uiState: UiState
    private lateinit var incrementButton: Button
    private lateinit var decrementButton: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        incrementButton = findViewById(R.id.incrementButton)
        decrementButton = findViewById(R.id.decrementButton)
        textView = findViewById(R.id.countTextView)

        incrementButton.setOnClickListener {
            uiState = count.increment(textView.text.toString())
            uiState.apply(decrementButton, incrementButton, textView)
        }
        decrementButton.setOnClickListener {
            uiState = count.decrement(textView.text.toString())
            uiState.apply(decrementButton, incrementButton, textView)
        }

        if (savedInstanceState == null) {
            uiState = count.initial(textView.text.toString())
            uiState.apply(decrementButton, incrementButton, textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        uiState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, UiState::class.java) as UiState
        } else {
            savedInstanceState.getSerializable(KEY) as UiState
        }
        uiState.apply(decrementButton, incrementButton, textView)
    }

    companion object {
        private const val KEY = "uiStateKey"
    }
}


interface Count {
    fun initial(number: String): UiState
    fun increment(number: String): UiState
    fun decrement(number: String): UiState

    class Base(private val step: Int, private val max: Int, private val min: Int) : Count {

        init {
            if (step < 1)
                throw IllegalStateException("step should be positive, but was $step")
            if (max < 1)
                throw IllegalStateException("max should be positive, but was $max")
            if (max < step)
                throw IllegalStateException("max should be more than step")
            if (max < min)
                throw IllegalStateException("max should be more than min")
        }

        override fun initial(number: String): UiState {
            val digits = number.toInt()
            return when (digits) {
                min -> UiState.Min(number)
                max -> UiState.Max(number)
                else -> UiState.Base(number)
            }
        }

        override fun increment(number: String): UiState {
            val digits = number.toInt()
            val result = digits + step
            return initial(result.toString())
        }

        override fun decrement(number: String): UiState {
            val digits = number.toInt()
            val result = digits - step
            return initial(result.toString())
        }


    }


}

interface UiState : Serializable {

    fun apply(decrementButton: Button, incrementButton: Button, textView: TextView)

    class Min(private val text: String) : UiState {
        override fun apply(decrementButton: Button, incrementButton: Button, textView: TextView) {
            textView.text = text
            decrementButton.isEnabled = false
            incrementButton.isEnabled = true
        }
    }

    class Base(private val text: String) : UiState {
        override fun apply(decrementButton: Button, incrementButton: Button, textView: TextView) {
            textView.text = text
            decrementButton.isEnabled = true
            incrementButton.isEnabled = true
        }
    }

    class Max(private val text: String) : UiState {
        override fun apply(decrementButton: Button, incrementButton: Button, textView: TextView) {
            textView.text = text
            incrementButton.isEnabled = false
            decrementButton.isEnabled = true
        }
    }


}