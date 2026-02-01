package iuh.fit.bai2;

import iuh.fit.bai2.decorator.*;
import iuh.fit.bai2.factory.BookFactory;
import iuh.fit.bai2.model.Book;
import iuh.fit.bai2.observer.LibrarianObserver;
import iuh.fit.bai2.observer.UserObserver;
import iuh.fit.bai2.singleton.Library;
import iuh.fit.bai2.strategy.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Bai2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Bai2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== LIBRARY MANAGEMENT SYSTEM DEMO ===");
        demonstrateDesignPatterns();
    }

    private void demonstrateDesignPatterns() {
        Library library = Library.getInstance();
        
        // Observer Pattern Demo
        System.out.println("\n1. OBSERVER PATTERN DEMO:");
        library.addObserver(new LibrarianObserver("Alice"));
        library.addObserver(new UserObserver("john@email.com", "Fiction"));
        
        // Factory Method Pattern Demo
        System.out.println("\n2. FACTORY METHOD PATTERN DEMO:");
        BookFactory physicalFactory = BookFactory.getFactory("physical");
        BookFactory ebookFactory = BookFactory.getFactory("ebook");
        
        Book book1 = physicalFactory.createBook("P001", "The Hobbit", "J.R.R. Tolkien", "Fiction", "A1-01", 310);
        Book book2 = ebookFactory.createBook("E001", "Clean Code", "Robert Martin", "Programming", "https://library.com/ebooks/E001", 8.5);
        
        library.addBook(book1);
        library.addBook(book2);
        
        // Strategy Pattern Demo
        System.out.println("\n3. STRATEGY PATTERN DEMO:");
        library.setSearchStrategy(new SearchByTitle());
        System.out.println("Search by title 'Hobbit': " + library.searchBooks("Hobbit"));
        
        library.setSearchStrategy(new SearchByAuthor());
        System.out.println("Search by author 'Martin': " + library.searchBooks("Martin"));
        
        // Decorator Pattern Demo
        System.out.println("\n4. DECORATOR PATTERN DEMO:");
        BookRental basicRental = new BasicBookRental(book1, "john@email.com");
        System.out.println("Basic rental: " + basicRental.getRentalDescription() + " - Cost: $" + basicRental.getRentalCost());
        
        BookRental extendedRental = new ExtendedRentalDecorator(basicRental, 7);
        System.out.println("Extended rental: " + extendedRental.getRentalDescription() + " - Cost: $" + extendedRental.getRentalCost());
        
        BookRental brailleRental = new BrailleEditionDecorator(extendedRental);
        System.out.println("Braille + Extended: " + brailleRental.getRentalDescription() + " - Cost: $" + brailleRental.getRentalCost());
        
        // Borrow and return demo
        System.out.println("\n5. BORROW/RETURN DEMO:");
        library.borrowBook(basicRental);
        System.out.println("Available books after borrowing: " + library.getAvailableBooks().size());
        
        library.returnBook(book1.getId());
        System.out.println("Available books after returning: " + library.getAvailableBooks().size());
        
        System.out.println("\n=== DEMO COMPLETED ===");
        System.out.println("REST API is available at: http://localhost:8080/api/library");
    }
}
