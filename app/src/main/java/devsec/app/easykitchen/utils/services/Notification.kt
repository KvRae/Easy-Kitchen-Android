package devsec.app.easykitchen.utils.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import devsec.app.easykitchen.R

const val NOTIFICATION_ID = 1
const val CHANNEL_ID = "channel-id"

const val NOTIFICATION_TITLE = "notification-title"
const val NOTIFICATION_MESSAGE = "notification-message"

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification  = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setContentTitle(intent?.getStringExtra(NOTIFICATION_TITLE))
            .setContentText(intent?.getStringExtra(NOTIFICATION_MESSAGE))
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)


    }
}