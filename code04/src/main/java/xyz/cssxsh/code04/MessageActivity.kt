package xyz.cssxsh.code04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        findViewById<TextView>(R.id.info)?.apply {
            text = intent.getCharSequenceExtra(MainActivity.MESSAGE_STRING)
        }
    }
}