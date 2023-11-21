package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private var state: State = State.Initial
    private lateinit var linearLayout: LinearLayout
    private lateinit var textView: TextView
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayout = findViewById<LinearLayout>(R.id.rootLayout)
        textView = findViewById(R.id.titleTextView)
        button = findViewById(R.id.removeButton)


        button.setOnClickListener {
            state = State.Removed

            state.apply(linearLayout, textView, button)

        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            state = savedInstanceState.getSerializable(KEY, State::class.java) as State
        } else {
            state = savedInstanceState.getSerializable(KEY) as State
        }
        state.apply(linearLayout, textView, button)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val KEY = "removeKey"
    }
}

interface State : Serializable {


    fun apply(linearLayout: LinearLayout, textView: TextView, button: Button)

    object Initial : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView, button: Button) = Unit
    }

    object Removed : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView, button: Button) {
            linearLayout.removeView(textView)
            button.isEnabled = false
        }
    }


}