package iuh.fit.bai2.observer;

import iuh.fit.bai2.model.Book;

/**
 * Concrete observer for user notifications
 */
public class UserObserver implements LibraryObserver {
    private String email;
    private String interestedGenre;

    public UserObserver(String email, String interestedGenre) {
        this.email = email;
        this.interestedGenre = interestedGenre;
    }

    @Override
    public void onBookAdded(Book book) {
        if (book.getGenre().equalsIgnoreCase(interestedGenre)) {
            System.out.println("[EMAIL TO " + email + "] New " + interestedGenre + " book available: " + book.getTitle());
        }
    }

    @Override
    public void onBookBorrowed(Book book, String borrower) {
        // Users typically don't need to know about other people's borrowing
    }

    @Override
    public void onBookReturned(Book book, String borrower) {
        if (book.getGenre().equalsIgnoreCase(interestedGenre)) {
            System.out.println("[EMAIL TO " + email + "] Book now available: " + book.getTitle());
        }
    }

    @Override
    public void onBookOverdue(Book book, String borrower) {
        if (borrower.equals(email)) {
            System.out.println("[EMAIL TO " + email + "] REMINDER: Your book is overdue: " + book.getTitle());
        }
    }

    public String getEmail() { return email; }
    public String getInterestedGenre() { return interestedGenre; }
}