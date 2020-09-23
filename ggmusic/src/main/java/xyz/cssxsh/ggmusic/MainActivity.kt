package xyz.cssxsh.ggmusic

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

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
    }

    @RequiresApi(Build.VERSION_CODES.M)
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
}