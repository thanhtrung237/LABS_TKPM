package iuh.fit.bai2.observer;

import iuh.fit.bai2.model.Book;

/**
 * Observer interface for library notifications
 */
public interface LibraryObserver {
    void onBookAdded(Book book);
    void onBookBorrowed(Book book, String borrower);
    void onBookReturned(Book book, String borrower);
    void onBookOverdue(Book book, String borrower);
}