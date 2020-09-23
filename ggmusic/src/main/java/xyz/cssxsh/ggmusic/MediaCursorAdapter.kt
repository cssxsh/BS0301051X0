package xyz.cssxsh.ggmusic

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class MediaCursorAdapter(
    mConText: Context
) : CursorAdapter(mConText, null, 0) {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mConText)

    private val unknownText: String by lazy {
        mConText.resources.getString(R.string.unknown)
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View? =
        mLayoutInflater.inflate(R.layout.list_item, parent, false)?.apply {
            tag = object : ViewHolder {
                override var tvTitle: TextView = findViewById(R.id.tv_title)
                override var tvArtist: TextView = findViewById(R.id.tv_artist)
                override var tvOrder: TextView = findViewById(R.id.tv_order)
                override var divider: View = findViewById(R.id.divider)
            }
        }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        view?.tag?.let { it as ViewHolder }?.apply {
            tvTitle.text = cursor?.run {
                getString(getColumnIndex(MediaStore.Audio.Media.TITLE))
            } ?: unknownText
            tvArtist.text = cursor?.run {
                getString(getColumnIndex(MediaStore.Audio.Media.ARTIST))
            } ?: unknownText
            tvOrder.text = cursor?.run {
                (position + 1).toString()
            } ?: unknownText
        }
    }

    interface ViewHolder {
        var tvTitle: TextView
        var tvArtist: TextView
        var tvOrder: TextView
        var divider: View
    }
}