package xyz.cssxsh.code04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        val MESSAGE_STRING = "${this::class.qualifiedName}.MESSAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.send_message)?.setOnClickListener {
            findViewById<EditText>(R.id.message)?.also {
                Toast.makeText(this, it.text, Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MessageActivity::class.java).apply {
                    putExtra(MESSAGE_STRING, it.text)
                })
            }
        }
    }
}