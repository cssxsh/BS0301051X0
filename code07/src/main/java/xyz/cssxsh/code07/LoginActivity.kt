package xyz.cssxsh.code07

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var seePassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val spFileName = resources.getString(R.string.shared_preferences_file_name)
        val accountKey = resources.getString(R.string.login_account_name)
        val passwordKey = resources.getString(R.string.login_password)
        val rememberPasswordKey = resources.getString(R.string.login_remember_password)

        getSharedPreferences(spFileName, MODE_PRIVATE).let {
            et_username.text = SpannableStringBuilder(it.getString(accountKey, ""))
            et_password.text = SpannableStringBuilder(it.getString(passwordKey, ""))
            cb_remember.isChecked = it.getBoolean(rememberPasswordKey, false)
        }

        see_switch.setOnClickListener {
            seePassword = !seePassword
            if (seePassword) {
                et_password.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                see_switch.setImageResource(R.drawable.ic_baseline_visibility_24)
            } else {
                et_password.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                see_switch.setImageResource(R.drawable.ic_baseline_visibility_off_24)
            }
        }

        bt_login.setOnClickListener {
            getSharedPreferences(spFileName, Context.MODE_PRIVATE).edit().apply {
                if (cb_remember.isChecked) {
                    putString(accountKey, et_username.text.toString())
                    putString(passwordKey, et_password.text.toString())
                    putBoolean(rememberPasswordKey, true)
                } else {
                    remove(accountKey)
                    remove(passwordKey)
                    remove(rememberPasswordKey)
                }
            }.apply()
            startActivity(Intent(this, DetailActivity::class.java))
        }
    }
}
