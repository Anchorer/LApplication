// IOnNewBookArrivedListener.aidl
package org.anchorer.l.c02.aidl;

import org.anchorer.l.c02.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
