package xyz.cssxsh.code05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var seePassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        see_switch.setOnClickListener {
            seePassword = !seePassword
            if (seePassword) {
                password.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                see_switch.setImageResource(R.drawable.ic_baseline_visibility_24)
            } else {
                password.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                see_switch.setImageResource(R.drawable.ic_baseline_visibility_off_24)
            }
        }
    }
}
