package xyz.cssxsh.code2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tvCount).text = count.toString()

        findViewById<Button>(R.id.btnShowToast).setOnClickListener {
            Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnCount).setOnClickListener {
            findViewById<TextView>(R.id.tvCount).text = (++count).toString()
        }
    }
}