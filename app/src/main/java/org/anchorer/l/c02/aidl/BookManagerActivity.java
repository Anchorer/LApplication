package org.anchorer.l.c02.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.anchorer.l.c.Const;

import java.util.List;

/**
 * Created by Anchorer/duruixue on 2015/9/30.
 */
public class BookManagerActivity extends AppCompatActivity {

    private IBookManager mBookManager;

    private final int MSG_RECEIVE_BOOK = 1;
    private Handler mHandler = new Handler(message -> {
        switch (message.what) {
            case MSG_RECEIVE_BOOK: {
                Log.d(Const.LOG, "Receive Book: " + message.obj);
                break;
            }
        }
        return false;
    });

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(Const.LOG, "Service Connected.");
            IBookManager manager = IBookManager.Stub.asInterface(iBinder);
            try {
                mBookManager = manager;
                List<Book> bookList = manager.getBookList();
                Log.d(Const.LOG, "query book list, list type: " + bookList.getClass().getCanonicalName());
                Log.d(Const.LOG, "query book list: " + bookList.toString());
                manager.registerListener(mListener);
            } catch (RemoteException e) {
                Log.e(Const.LOG, "ServiceConnection Exception", e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBookManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.unregisterlistener(mListener);
            } catch (RemoteException e) {
                Log.e(Const.LOG, "Unregister Listener Exception", e);
            }
        }
        unbindService(mConnection);
    }

    private IOnNewBookArrivedListener mListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(MSG_RECEIVE_BOOK, book).sendToTarget();
        }
    };

}
