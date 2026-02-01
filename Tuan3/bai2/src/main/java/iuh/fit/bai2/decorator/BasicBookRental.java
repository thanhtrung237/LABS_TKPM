package iuh.fit.bai2.decorator;

import iuh.fit.bai2.model.Book;
import java.time.LocalDate;

/**
 * Basic book rental implementation
 */
public class BasicBookRental implements BookRental {
    private Book book;
    private String borrower;
    private LocalDate dueDate;

    public BasicBookRental(Book book, String borrower) {
        this.book = book;
        this.borrower = borrower;
        this.dueDate = LocalDate.now().plusDays(14); // Default 2 weeks
    }

    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public String getBorrower() {
        return borrower;
    }

    @Override
    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public double getRentalCost() {
        return book.getRentalPrice();
    }

    @Override
    public String getRentalDescription() {
        return "Basic rental of " + book.getTitle();
    }
}