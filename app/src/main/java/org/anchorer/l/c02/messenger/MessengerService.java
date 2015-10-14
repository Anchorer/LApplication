package org.anchorer.l.c02.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Anchorer/duruixue on 2015/9/29.
 */
public class MessengerService extends Service {

    private static final String TAG = "L";

    private Messenger mMessenger = new Messenger(new Handler(msg -> {
        switch (msg.what) {
            case 1: {
                Bundle data = msg.getData();
                Log.d(TAG, "onReceive: " + data.getString("msg"));
                Messenger messenger = msg.replyTo;
                Message replyMsg = new Message();
                replyMsg.what = 2;
                Bundle reply = new Bundle();
                reply.putString("reply", "稍后再给你回复！！！");
                replyMsg.setData(reply);
                try {
                    messenger.send(replyMsg);
                } catch (RemoteException e) {
                    Log.e(TAG, "Send Exception", e);
                }
                break;
            }
        }
        return false;
    }));

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

}
