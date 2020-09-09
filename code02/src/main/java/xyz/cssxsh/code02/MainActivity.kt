package xyz.cssxsh.code02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        private val COUNT_VALUE = "${this::class.qualifiedName}.COUNT_VALUE"
    }

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowToast.setOnClickListener {
            Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show()
        }

        btnCount.setOnClickListener {
            tvCount.text = (++count).toString()
        }
    }

    override fun onStart() {
        super.onStart()
        tvCount.text = count.toString()
    }
    /**
     * EXP5
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply { putInt(COUNT_VALUE, count) })
    }
    /**
     * EXP5
      */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState.also { count = it.getInt(COUNT_VALUE) })
        tvCount.text = count.toString()
    }
}