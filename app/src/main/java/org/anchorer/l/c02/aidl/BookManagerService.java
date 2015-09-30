package org.anchorer.l.c02.aidl;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import org.anchorer.l.c.Const;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Anchorer/duruixue on 2015/9/30.
 */
public class BookManagerService extends Service {

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBooklist = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    private IBinder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooklist;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBooklist.add(book);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
            Log.d(Const.LOG, "RegisterListener: " + mListenerList.getRegisteredCallbackCount());
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unregisterlistener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
            Log.d(Const.LOG, "UnRegisterListener: " + mListenerList.getRegisteredCallbackCount());
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Permission check
        int check = checkCallingOrSelfPermission("org.anchorer.l.c02.aidl.ACCESS_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.e(Const.LOG, "Permisssion Denied.");
            return null;
        }
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBooklist.add(new Book(1, "Anchorer"));
        mBooklist.add(new Book(2, "iOS"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroyed.set(true);
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBooklist.add(book);
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                listener.onNewBookArrived(book);
            }
        }
        mListenerList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Log.e(Const.LOG, "InterruptedException", e);
                }
                int newBookId = mBooklist.size() + 1;
                Book newBook = new Book(newBookId, "Book#" + newBookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    Log.e(Const.LOG, "OnNewBookArrived Exception", e);
                }
            }
        }
    }

}
