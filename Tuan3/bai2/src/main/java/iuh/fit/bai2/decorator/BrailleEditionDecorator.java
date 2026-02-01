package iuh.fit.bai2.decorator;

/**
 * Decorator for Braille edition books
 */
public class BrailleEditionDecorator extends RentalDecorator {

    public BrailleEditionDecorator(BookRental rental) {
        super(rental);
    }

    @Override
    public double getRentalCost() {
        return rental.getRentalCost() + 2.0; // Additional cost for Braille edition
    }

    @Override
    public String getRentalDescription() {
        return rental.getRentalDescription() + " + Braille Edition";
    }
}