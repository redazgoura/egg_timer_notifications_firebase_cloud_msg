package com.example.android.eggtimernotifications

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.android.eggtimernotifications.util.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d("RemoteMessage", "From : ${remoteMessage?.from}")

        // Check if message contains a data payload.
        remoteMessage?.data?.let {
            Log.d("RemoteMessage", "Msg data payload  : ${remoteMessage.data}")
        }

        // check if message contains notification payload
        remoteMessage?.notification?.let {
            Log.d("RemoteMessage", "Msg notification payload  : ${it.body}")
            sendNotification(it.body!!)
        }
    }

    /**
     * called when message is received */
    /** Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String?) {

        Log.d("Token", "Refreshed Token : $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }

    // create & show a simple notif containing the received FCM msg
    private  fun sendNotification(messageBody : String){
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        )
        notificationManager!!.sendNotification(messageBody, applicationContext)
    }
}