package xyz.cssxsh.code05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import xyz.cssxsh.code02.R

class MainActivity : AppCompatActivity() {
    private var count = 0

    companion object {
        @JvmStatic
        private val COUNT_VALUE = "${this::class.qualifiedName}.COUNT_VALUE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnShowToast)?.setOnClickListener {
            Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnCount)?.setOnClickListener {
            findViewById<TextView>(R.id.tvCount)?.text = (++count).toString()
        }
    }

    override fun onStart() {
        super.onStart()
        findViewById<TextView>(R.id.tvCount)?.text = count.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply { putInt(COUNT_VALUE, count) })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState.also { count = it.getInt(COUNT_VALUE) })
        findViewById<TextView>(R.id.tvCount)?.text = count.toString()
    }
}