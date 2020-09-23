package xyz.cssxsh.code07

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.list_item.view.*

class NewsAdapter(
    mContext: Context,
    private val resourceId: Int,
    mNewsData: List<News>
) : ArrayAdapter<News>(mContext, resourceId, mNewsData) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        LayoutInflater.from(context).inflate(resourceId, parent, false).apply {
            getItem(position)?.let {
                tv_title.text = it.mTitle
                tv_subtitle.text = it.mAuthor
                iv_image.setImageResource(it.mImageId)
            }
        }.let { requireNotNull(it) }
}