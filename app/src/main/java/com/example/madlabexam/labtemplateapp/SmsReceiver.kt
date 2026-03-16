package com.example.labtemplateapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast

/**
 * VERY BASIC SMS RECEIVER
 * Note: To use this in an exam:
 * 1. Go to AndroidManifest.xml and uncomment the <uses-permission> for SMS
 * 2. Uncomment the <receiver> tag for SmsReceiver in AndroidManifest.xml
 */
class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // If the broadcast is exactly an SMS_RECEIVED action
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            
            // Telephony.Sms.Intents helps us extract the message easily
            val smsList = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            
            for (sms in smsList) {
                // Get phone number
                val senderPhone = sms.displayOriginatingAddress
                // Get the text message itself
                val messageText = sms.messageBody
                
                // Show a toast that we got an SMS!
                Toast.makeText(context, "SMS from $senderPhone: $messageText", Toast.LENGTH_LONG).show()
            }
        }
    }
}
