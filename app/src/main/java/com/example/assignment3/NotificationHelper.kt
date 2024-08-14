package com.example.assignment3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotificationHelper(private val context: Context) {

    private val CHANNEL_ID = "notification_channel"
    private val NOTIFICATION_ID = 1

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Channel"
            val descriptionText = "Channel for notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendDepositNotification(transactionAmount: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Amount Deposited")
            .setContentText("A credit of $transactionAmount CAD has been made to your account.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        } else {
            // Handle the case where permission is not granted
            Toast.makeText(context, "Notification permission is not granted. Please enable it in settings.", Toast.LENGTH_LONG).show()
        }
    }
    fun sendWithdrawNotification(transactionAmount: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Amount Withdrawal")
            .setContentText("A debit of $transactionAmount CAD has been made on your account.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        } else {
            // Handle the case where permission is not granted
            Toast.makeText(context, "Notification permission is not granted. Please enable it in settings.", Toast.LENGTH_LONG).show()
        }
    }
}
