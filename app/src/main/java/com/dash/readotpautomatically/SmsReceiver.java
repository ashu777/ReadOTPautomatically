package com.dash.readotpautomatically;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListener mListener;
    Boolean b;
    String otpnumber;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            b=sender.endsWith("INFOSM");  //Just to fetch otp sent from INFOSM.
            String messageBody = smsMessage.getMessageBody();
            otpnumber=messageBody.replaceAll("[^0-9]","");   // here otpnumber contains otp
            // which is in number format
            //Pass on the text to our listener.
            if(b) {
                mListener.messageReceived(otpnumber);  // attach value to the interface
                //object
            }

        }
    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
