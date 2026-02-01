package iuh.fit.bai2.decorator;

import iuh.fit.bai2.model.Book;
import java.time.LocalDate;

/**
 * Base interface for book rental
 */
public interface BookRental {
    Book getBook();
    String getBorrower();
    LocalDate getDueDate();
    double getRentalCost();
    String getRentalDescription();
}