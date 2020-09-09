package xyz.cssxsh.code06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val authors by lazy { requireNotNull(resources.getStringArray(R.array.authors)) }

    private val titles by lazy { requireNotNull(resources.getStringArray(R.array.titles)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv_news_list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titles)
    }
}
