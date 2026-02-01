package iuh.fit.bai2.service;

import iuh.fit.bai2.decorator.*;
import iuh.fit.bai2.factory.BookFactory;
import iuh.fit.bai2.model.Book;
import iuh.fit.bai2.observer.LibrarianObserver;
import iuh.fit.bai2.observer.UserObserver;
import iuh.fit.bai2.singleton.Library;
import iuh.fit.bai2.strategy.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for library operations
 */
@Service
public class LibraryService {
    private final Library library;

    public LibraryService() {
        this.library = Library.getInstance();
        initializeLibrary();
    }

    private void initializeLibrary() {
        // Add some observers
        library.addObserver(new LibrarianObserver("Alice"));
        library.addObserver(new LibrarianObserver("Bob"));
        library.addObserver(new UserObserver("user1@email.com", "Fiction"));
        library.addObserver(new UserObserver("user2@email.com", "Science"));

        // Add some sample books
        addSampleBooks();
    }

    private void addSampleBooks() {
        // Create books using Factory Pattern
        BookFactory physicalFactory = BookFactory.getFactory("physical");
        BookFactory ebookFactory = BookFactory.getFactory("ebook");
        BookFactory audioFactory = BookFactory.getFactory("audio");

        library.addBook(physicalFactory.createBook("P001", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "A1-01", 180));
        library.addBook(physicalFactory.createBook("P002", "To Kill a Mockingbird", "Harper Lee", "Fiction", "A1-02", 281));
        library.addBook(ebookFactory.createBook("E001", "1984", "George Orwell", "Dystopian", "https://library.com/ebooks/E001", 6.2));
        library.addBook(audioFactory.createBook("A001", "Dune", "Frank Herbert", "Science Fiction", "Scott Brick", 688));
    }

    public void addBook(String type, String id, String title, String author, String genre, Object... params) {
        BookFactory factory = BookFactory.getFactory(type);
        Book book = factory.createBook(id, title, author, genre, params);
        library.addBook(book);
    }

    public List<Book> getAllBooks() {
        return library.getAllBooks();
    }

    public List<Book> getAvailableBooks() {
        return library.getAvailableBooks();
    }

    public List<Book> searchBooksByTitle(String title) {
        library.setSearchStrategy(new SearchByTitle());
        return library.searchBooks(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        library.setSearchStrategy(new SearchByAuthor());
        return library.searchBooks(author);
    }

    public List<Book> searchBooksByGenre(String genre) {
        library.setSearchStrategy(new SearchByGenre());
        return library.searchBooks(genre);
    }

    public boolean borrowBook(String bookId, String borrower) {
        Book book = library.findBookById(bookId);
        if (book != null && book.isAvailable()) {
            BookRental rental = new BasicBookRental(book, borrower);
            return library.borrowBook(rental);
        }
        return false;
    }

    public boolean borrowBookWithExtension(String bookId, String borrower, int extraDays) {
        Book book = library.findBookById(bookId);
        if (book != null && book.isAvailable()) {
            BookRental rental = new BasicBookRental(book, borrower);
            rental = new ExtendedRentalDecorator(rental, extraDays);
            return library.borrowBook(rental);
        }
        return false;
    }

    public boolean borrowBookWithBraille(String bookId, String borrower) {
        Book book = library.findBookById(bookId);
        if (book != null && book.isAvailable()) {
            BookRental rental = new BasicBookRental(book, borrower);
            rental = new BrailleEditionDecorator(rental);
            return library.borrowBook(rental);
        }
        return false;
    }

    public boolean borrowBookWithTranslation(String bookId, String borrower, String language) {
        Book book = library.findBookById(bookId);
        if (book != null && book.isAvailable()) {
            BookRental rental = new BasicBookRental(book, borrower);
            rental = new TranslatedEditionDecorator(rental, language);
            return library.borrowBook(rental);
        }
        return false;
    }

    public boolean returnBook(String bookId) {
        return library.returnBook(bookId);
    }

    public BookRental getRentalInfo(String bookId) {
        return library.getRental(bookId);
    }

    public List<BookRental> getAllActiveRentals() {
        return library.getAllActiveRentals();
    }
}