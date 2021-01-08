package xyz.cssxsh.ggmusic

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_media_toolbar.view.*

class MainActivity : AppCompatActivity() {

    private val mCursorAdapter: MediaCursorAdapter by lazy {
        MediaCursorAdapter(this)
    }

    companion object {
        const val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSION_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        const val SELECTION =
            MediaStore.Audio.Media.IS_MUSIC + " = ? " + " AND " + MediaStore.Audio.Media.MIME_TYPE + " LIKE ? "
        val SELECTION_ARGS = arrayOf(
            (1).toString(),
            "audio/mpeg"
        )
        val DATA_URI = "${this::class.qualifiedName}.DATA_URI"
        val TITLE = "${this::class.qualifiedName}.TITLE"
        val ARTIST = "${this::class.qualifiedName}.ARTIST"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv_playlist.adapter = mCursorAdapter

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            requestPermissions(PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE)
        } else {
            initPlaylist()
        }

        nav_view.let {
            LayoutInflater.from(this).inflate(R.layout.bottom_media_toolbar, it, true)
            // TODO: nav_view.iv_play.setOnClickListener(this)
            lv_playlist.onItemClickListener = onItemClickListener
            it.visibility = View.GONE
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> if (PackageManager.PERMISSION_GRANTED in grantResults) {
                initPlaylist()
            }
        }
    }

    private fun initPlaylist() = contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        emptyArray(),
        SELECTION,
        SELECTION_ARGS,
        MediaStore.Audio.Media.DEFAULT_SORT_ORDER
    )?.also { cursor ->
        mCursorAdapter.swapCursor(cursor)
        mCursorAdapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val onItemClickListener = OnItemClickListener { _, _, position, _ ->
        Log.d(null, "sss")
        mCursorAdapter.cursor.takeIf { it.moveToPosition(position) }?.apply {
            val data = getString(getColumnIndex(MediaStore.Audio.Media.DATA))
            val title = getString(getColumnIndex(MediaStore.Audio.Media.TITLE))
            val artist = getString(getColumnIndex(MediaStore.Audio.Media.ARTIST))
            startService(Intent(this@MainActivity, MusicService::class.java).apply {
                putExtra(DATA_URI, data)
                putExtra(TITLE, title)
                putExtra(ARTIST, artist)
            })

            nav_view.apply {
                visibility = View.VISIBLE
                tv_bottom_title.text = title
                tv_bottom_artist.text = artist
                ContentUris.withAppendedId(
                    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    getLong(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                ).let {
                    contentResolver.query(it, null, null, null)
                }?.takeIf {
                    it.moveToFirst()
                }?.let {
                    Glide.with(this)
                        .load(it.getString(it.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)))
                        .into(iv_thumbnail)
                    it.close()
                }
            }
        }
    }

}