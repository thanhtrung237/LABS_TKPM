package iuh.fit.bai2.decorator;

import java.time.LocalDate;

/**
 * Decorator for extended rental period
 */
public class ExtendedRentalDecorator extends RentalDecorator {
    private int extraDays;

    public ExtendedRentalDecorator(BookRental rental, int extraDays) {
        super(rental);
        this.extraDays = extraDays;
    }

    @Override
    public LocalDate getDueDate() {
        return rental.getDueDate().plusDays(extraDays);
    }

    @Override
    public double getRentalCost() {
        return rental.getRentalCost() + (extraDays * 0.5); // $0.5 per extra day
    }

    @Override
    public String getRentalDescription() {
        return rental.getRentalDescription() + " + Extended for " + extraDays + " days";
    }
}