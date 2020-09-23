package xyz.cssxsh.code07

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.getResourceIdOrThrow
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val dataList by lazy {
        val authors = requireNotNull(resources.getStringArray(R.array.authors))
        val titles = requireNotNull(resources.getStringArray(R.array.titles))
        val images = requireNotNull(resources.obtainTypedArray(R.array.images))
        (1..17).map { News(mTitle = titles[it], mAuthor = authors[it], mImageId = images.getResourceIdOrThrow(it)) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        lv_news_list.adapter = NewsAdapter(this, R.layout.list_item, dataList)
    }
}
