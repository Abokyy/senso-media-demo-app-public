package hu.benefanlabs.sensomediademo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging
import hu.benefanlabs.sensomediademo.features.FeaturesModule
import hu.benefanlabs.sensomediademo.features.home.HomeViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class NotificationService : FirebaseMessagingService(), DIAware {

    override val di: DI by lazy { FeaturesModule }
    private val homeViewModel: HomeViewModel by instance()

    companion object {
        private const val CHANNEL_ID = "default_channel"
        private const val NOTI_ID = 1
        private const val REQUEST_CODE = 3
        private const val CHANNEL_NAME = "CHANNEL NAME"
        private const val CHANNEL_DESCRIPTION = "CHANNEL DESCRIPTION"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage)
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val notificationManager =
            applicationContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = createNotification(remoteMessage)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val description = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                CHANNEL_ID,
                name,
                importance
            )
            channel.description = description
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTI_ID, notification)
    }

    private fun createNotification(remoteMessage: RemoteMessage): Notification {

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.senso_icon)
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["body"])
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (remoteMessage.data["info"] != null) {
            val info = remoteMessage.data["info"]
            val taskDetailIntent = Intent(
                Intent.ACTION_VIEW,
                "custom://hu.benefenlabs.sensomediademoapp/$info".toUri()
            )

            val pendingIntent: PendingIntent = TaskStackBuilder.create(applicationContext).run {
                addNextIntentWithParentStack(taskDetailIntent)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_IMMUTABLE)!!
                } else {
                    getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT)!!
                }
            }
            builder.setContentIntent(pendingIntent)
            homeViewModel.sendWish(
                HomeViewModel.Wish.SetWelcomeMessage(
                    remoteMessage.data["info"]!!
                )
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }
        return builder.build()
    }

    override fun onNewToken(token: String) {

    }
}
