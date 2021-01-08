package xyz.cssxsh.ggmusic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MusicService : Service() {

    companion object{
        const val ONGOING_NOTIFICATION_ID = 1001
        val CHANNEL_ID = "${this::class.qualifiedName}.CHANNEL_ID"
        const val CHANNEL_NAME = "Music channel"
    }

    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer()
        }
    }

    override fun onDestroy() {
        mMediaPlayer = null
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getSystemService(NOTIFICATION_SERVICE)?.let { val mNotificationManager = it as NotificationManager
            it.createNotificationChannel(NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH))
        }
        requireNotNull(intent).let {
            val data = Uri.parse(it.getStringExtra(MainActivity.DATA_URI))
            val title = it.getStringExtra(MainActivity.TITLE)
            val artist = it.getStringExtra(MainActivity.ARTIST)
            mMediaPlayer?.runCatching {
                reset()
                setDataSource(applicationContext, data)
                prepare()
                start()
            }?.onFailure {
                Log.getStackTraceString(it)
            }



            val notificationIntent = Intent(applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity( applicationContext, 0, notificationIntent, 0)
            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
                setContentTitle(title)
                setContentText(artist)
                setSmallIcon(ONGOING_NOTIFICATION_ID, R.drawable.ic_launcher_foreground)
                setContentIntent(pendingIntent)
            }.build()
            startForeground(ONGOING_NOTIFICATION_ID, notification)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}