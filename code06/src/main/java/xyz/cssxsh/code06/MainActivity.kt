package xyz.cssxsh.code06

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.core.content.res.getResourceIdOrThrow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dataList by lazy {
        val authors = requireNotNull(resources.getStringArray(R.array.authors))
        val titles = requireNotNull(resources.getStringArray(R.array.titles))
        val images = requireNotNull(resources.obtainTypedArray(R.array.images))
        (1..17).map { News(mTitle = titles[it], mAuthor = authors[it], mImageId = images.getResourceIdOrThrow(it)) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv_news_list.adapter = NewsAdapter(this, R.layout.list_item, dataList)
    }
}
