package iuh.fit.bai2.observer;

import iuh.fit.bai2.model.Book;

/**
 * Concrete observer for librarian notifications
 */
public class LibrarianObserver implements LibraryObserver {
    private String name;

    public LibrarianObserver(String name) {
        this.name = name;
    }

    @Override
    public void onBookAdded(Book book) {
        System.out.println("[LIBRARIAN " + name + "] New book added: " + book.getTitle());
    }

    @Override
    public void onBookBorrowed(Book book, String borrower) {
        System.out.println("[LIBRARIAN " + name + "] Book borrowed: " + book.getTitle() + " by " + borrower);
    }

    @Override
    public void onBookReturned(Book book, String borrower) {
        System.out.println("[LIBRARIAN " + name + "] Book returned: " + book.getTitle() + " by " + borrower);
    }

    @Override
    public void onBookOverdue(Book book, String borrower) {
        System.out.println("[LIBRARIAN " + name + "] OVERDUE ALERT: " + book.getTitle() + " borrowed by " + borrower);
    }

    public String getName() { return name; }
}