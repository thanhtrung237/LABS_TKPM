package iuh.fit.bai2.controller;

import iuh.fit.bai2.decorator.BookRental;
import iuh.fit.bai2.model.Book;
import iuh.fit.bai2.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for library operations
 */
@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(libraryService.getAllBooks());
    }

    @GetMapping("/books/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        return ResponseEntity.ok(libraryService.getAvailableBooks());
    }

    @GetMapping("/books/search/title")
    public ResponseEntity<List<Book>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(libraryService.searchBooksByTitle(title));
    }

    @GetMapping("/books/search/author")
    public ResponseEntity<List<Book>> searchByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(libraryService.searchBooksByAuthor(author));
    }

    @GetMapping("/books/search/genre")
    public ResponseEntity<List<Book>> searchByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(libraryService.searchBooksByGenre(genre));
    }

    @PostMapping("/books")
    public ResponseEntity<String> addBook(@RequestBody Map<String, Object> bookData) {
        try {
            String type = (String) bookData.get("type");
            String id = (String) bookData.get("id");
            String title = (String) bookData.get("title");
            String author = (String) bookData.get("author");
            String genre = (String) bookData.get("genre");
            
            Object[] params = {};
            if (bookData.containsKey("params")) {
                @SuppressWarnings("unchecked")
                List<Object> paramsList = (List<Object>) bookData.get("params");
                params = paramsList.toArray();
            }
            
            libraryService.addBook(type, id, title, author, genre, params);
            return ResponseEntity.ok("Book added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding book: " + e.getMessage());
        }
    }

    @PostMapping("/books/{bookId}/borrow")
    public ResponseEntity<String> borrowBook(@PathVariable String bookId, @RequestParam String borrower) {
        boolean success = libraryService.borrowBook(bookId, borrower);
        if (success) {
            return ResponseEntity.ok("Book borrowed successfully");
        } else {
            return ResponseEntity.badRequest().body("Book not available or not found");
        }
    }

    @PostMapping("/books/{bookId}/borrow/extended")
    public ResponseEntity<String> borrowBookWithExtension(
            @PathVariable String bookId, 
            @RequestParam String borrower,
            @RequestParam int extraDays) {
        boolean success = libraryService.borrowBookWithExtension(bookId, borrower, extraDays);
        if (success) {
            return ResponseEntity.ok("Book borrowed with extension successfully");
        } else {
            return ResponseEntity.badRequest().body("Book not available or not found");
        }
    }

    @PostMapping("/books/{bookId}/borrow/braille")
    public ResponseEntity<String> borrowBookWithBraille(@PathVariable String bookId, @RequestParam String borrower) {
        boolean success = libraryService.borrowBookWithBraille(bookId, borrower);
        if (success) {
            return ResponseEntity.ok("Braille edition book borrowed successfully");
        } else {
            return ResponseEntity.badRequest().body("Book not available or not found");
        }
    }

    @PostMapping("/books/{bookId}/borrow/translated")
    public ResponseEntity<String> borrowBookWithTranslation(
            @PathVariable String bookId, 
            @RequestParam String borrower,
            @RequestParam String language) {
        boolean success = libraryService.borrowBookWithTranslation(bookId, borrower, language);
        if (success) {
            return ResponseEntity.ok("Translated edition book borrowed successfully");
        } else {
            return ResponseEntity.badRequest().body("Book not available or not found");
        }
    }

    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<String> returnBook(@PathVariable String bookId) {
        boolean success = libraryService.returnBook(bookId);
        if (success) {
            return ResponseEntity.ok("Book returned successfully");
        } else {
            return ResponseEntity.badRequest().body("Book rental not found");
        }
    }

    @GetMapping("/rentals")
    public ResponseEntity<List<BookRental>> getAllActiveRentals() {
        return ResponseEntity.ok(libraryService.getAllActiveRentals());
    }

    @GetMapping("/rentals/{bookId}")
    public ResponseEntity<BookRental> getRentalInfo(@PathVariable String bookId) {
        BookRental rental = libraryService.getRentalInfo(bookId);
        if (rental != null) {
            return ResponseEntity.ok(rental);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}