package iuh.fit.bai2.decorator;

import iuh.fit.bai2.model.Book;
import java.time.LocalDate;

/**
 * Abstract decorator for book rental
 */
public abstract class RentalDecorator implements BookRental {
    protected BookRental rental;

    public RentalDecorator(BookRental rental) {
        this.rental = rental;
    }

    @Override
    public Book getBook() {
        return rental.getBook();
    }

    @Override
    public String getBorrower() {
        return rental.getBorrower();
    }

    @Override
    public LocalDate getDueDate() {
        return rental.getDueDate();
    }

    @Override
    public double getRentalCost() {
        return rental.getRentalCost();
    }

    @Override
    public String getRentalDescription() {
        return rental.getRentalDescription();
    }
}