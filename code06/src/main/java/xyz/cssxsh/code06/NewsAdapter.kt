package xyz.cssxsh.code06

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item.view.*

class NewsAdapter(
    mContext: Context,
    private val resourceId: Int,
    mNewsData: List<News>
) : ArrayAdapter<News>(mContext, resourceId, mNewsData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        (convertView ?: LayoutInflater.from(context).inflate(
            resourceId,
            parent,
            false
        ).apply {
            tag = object : ViewHolder {
                override val tvTitle: TextView = tv_title
                override val tvAuthor: TextView = tv_subtitle
                override val ivImage: ImageView = iv_image
            }
        }).apply {
            (tag as ViewHolder).apply {
                getItem(position)?.let {
                    tvTitle.text = it.mTitle
                    tvAuthor.text = it.mAuthor
                    ivImage.setImageResource(it.mImageId)
                }
            }
        }.let { requireNotNull(it) }

    interface ViewHolder {
        val tvTitle: TextView
        val tvAuthor: TextView
        val ivImage: ImageView
    }
}