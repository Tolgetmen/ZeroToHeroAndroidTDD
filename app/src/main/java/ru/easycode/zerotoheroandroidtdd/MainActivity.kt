package ru.easycode.zerotoheroandroidtdd


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {


    private var uiState: UiState = UiState.Base("0")
    private val count = Count.Base(2, 4)

    private lateinit var textView: TextView
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textView = findViewById(R.id.countTextView)
        button = findViewById(R.id.incrementButton)

        button.setOnClickListener {
            uiState = count.increment(textView.text.toString())
            uiState.apply(textView, button)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            uiState = savedInstanceState.getSerializable(KEY, UiState::class.java) as UiState
        } else{
            uiState = savedInstanceState.getSerializable(KEY) as UiState
        }
        uiState.apply(textView, button)


    }



    companion object {
        private const val KEY = "uiStateKey"
    }
}


interface Count {
    fun increment(number: String): UiState

    class Base(private val step: Int, private val max: Int) : Count {

        init {
            if (step < 1)
                throw IllegalStateException("step should be positive, but was $step")
            if (max < 1)
                throw IllegalStateException("max should be positive, but was $max")
            if (max < step)
                throw IllegalStateException("max should be more than step")
        }

        override fun increment(number: String): UiState {
            val digits = number.toInt()
            val result = digits + step
            return if (result + step <= max) {
                UiState.Base(result.toString())
            } else {
                UiState.Max(result.toString())
            }

        }

    }
}

interface UiState : Serializable {



    fun apply(textView: TextView, button: Button)

    abstract class Abstract(private val text: String) : UiState{
        override fun apply(textView: TextView, button: Button) {
            textView.text = text
        }
    }
    data class Base(private val text: String) : Abstract(text)

    data class Max(private val text: String) : Abstract(text) {
        override fun apply(textView: TextView, button: Button) {
            super.apply(textView, button)
            button.isEnabled = false
        }

    }

}