// IBookManager.aidl
package org.anchorer.l.c02.aidl;

import org.anchorer.l.c02.aidl.Book;
import org.anchorer.l.c02.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterlistener(IOnNewBookArrivedListener listener);
}
