package iuh.fit.bai2.singleton;

import iuh.fit.bai2.decorator.BookRental;
import iuh.fit.bai2.model.Book;
import iuh.fit.bai2.observer.LibraryObserver;
import iuh.fit.bai2.strategy.SearchStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton Library class - ensures only one library instance exists
 */
public class Library {
    private static Library instance;
    private List<Book> books;
    private List<LibraryObserver> observers;
    private Map<String, BookRental> activeRentals;
    private SearchStrategy searchStrategy;

    private Library() {
        books = new ArrayList<>();
        observers = new ArrayList<>();
        activeRentals = new HashMap<>();
    }

    public static synchronized Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    // Observer pattern methods
    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LibraryObserver observer) {
        observers.remove(observer);
    }

    private void notifyBookAdded(Book book) {
        for (LibraryObserver observer : observers) {
            observer.onBookAdded(book);
        }
    }

    private void notifyBookBorrowed(Book book, String borrower) {
        for (LibraryObserver observer : observers) {
            observer.onBookBorrowed(book, borrower);
        }
    }

    private void notifyBookReturned(Book book, String borrower) {
        for (LibraryObserver observer : observers) {
            observer.onBookReturned(book, borrower);
        }
    }

    // Book management methods
    public void addBook(Book book) {
        books.add(book);
        notifyBookAdded(book);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .toList();
    }

    // Strategy pattern for search
    public void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }

    public List<Book> searchBooks(String searchTerm) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy not set");
        }
        return searchStrategy.search(books, searchTerm);
    }

    // Rental management
    public boolean borrowBook(BookRental rental) {
        Book book = rental.getBook();
        if (book.isAvailable()) {
            book.setAvailable(false);
            activeRentals.put(book.getId(), rental);
            notifyBookBorrowed(book, rental.getBorrower());
            return true;
        }
        return false;
    }

    public boolean returnBook(String bookId) {
        BookRental rental = activeRentals.get(bookId);
        if (rental != null) {
            Book book = rental.getBook();
            book.setAvailable(true);
            activeRentals.remove(bookId);
            notifyBookReturned(book, rental.getBorrower());
            return true;
        }
        return false;
    }

    public BookRental getRental(String bookId) {
        return activeRentals.get(bookId);
    }

    public List<BookRental> getAllActiveRentals() {
        return new ArrayList<>(activeRentals.values());
    }

    public Book findBookById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}