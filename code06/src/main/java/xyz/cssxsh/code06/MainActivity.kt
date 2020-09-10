package xyz.cssxsh.code06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        private val NEWS_TITLE = "news_title"

        @JvmStatic
        private val NEWS_AUTHOR = "news_author"
    }

    private val dataList by lazy {
        val authors = requireNotNull(resources.getStringArray(R.array.authors))
        val titles = requireNotNull(resources.getStringArray(R.array.titles))
        titles.mapIndexed { index, title ->
            mapOf(NEWS_TITLE to title, NEWS_AUTHOR to authors[index])
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv_news_list.adapter = SimpleAdapter(
            this,
            dataList,
            android.R.layout.simple_list_item_2,
            arrayOf(NEWS_TITLE, NEWS_AUTHOR),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
    }
}
