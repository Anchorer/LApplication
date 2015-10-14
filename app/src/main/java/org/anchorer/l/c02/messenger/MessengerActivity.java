package org.anchorer.l.c02.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Anchorer/duruixue on 2015/9/29.
 */
public class MessengerActivity extends Activity {

    private String TAG = "L";

    private Messenger mMessenger = new Messenger(new Handler(msg -> {
        switch (msg.what) {
            case 2: {
                Bundle data = msg.getData();
                Log.d(TAG, "Receiver Reply: " + data.getString("reply"));
                break;
            }
        }
        return false;
    }));

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Messenger messenger = new Messenger(iBinder);
            Message msg = new Message();
            msg.what = 1;
            Bundle data = new Bundle();
            data.putString("msg", "Hi! I'm Client!!");
            msg.setData(data);
            msg.replyTo = mMessenger;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                Log.e(TAG, "Send Message Exception", e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
