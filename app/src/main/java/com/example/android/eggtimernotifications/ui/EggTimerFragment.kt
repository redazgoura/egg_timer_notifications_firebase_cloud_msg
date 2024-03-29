
package com.example.android.eggtimernotifications.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.eggtimernotifications.R
import com.example.android.eggtimernotifications.databinding.FragmentEggTimerBinding
import com.google.firebase.messaging.FirebaseMessaging

class EggTimerFragment : Fragment() {

    private val TOPIC = "breakfast"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentEggTimerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_egg_timer, container, false
        )

        val viewModel = ViewModelProvider(this).get(EggTimerViewModel::class.java)

        binding.eggTimerViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        /**
         * call  createChannel to create a notification  */

        createChannel(
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )

        /**
         * create a new channel for FCM */
        createChannel(
            getString(R.string.breakfast_notification_channel_id),
            getString(R.string.breakfast_notification_channel_name)
        )

        /**
         * call subTopic() */
        subTopic()

        return binding.root
    }
    // subscribe to a topic
    private fun subTopic(){

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->

                var msg = getString(R.string.message_subscribed)

                if(!task.isSuccessful){
                    msg = getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
    }

    // create notification channel
    private fun createChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

                .apply {
                    setShowBadge(false)
                }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        fun newInstance() = EggTimerFragment()
    }
}

