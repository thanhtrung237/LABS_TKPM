package iuh.fit.bai2.decorator;

/**
 * Decorator for translated edition books
 */
public class TranslatedEditionDecorator extends RentalDecorator {
    private String language;

    public TranslatedEditionDecorator(BookRental rental, String language) {
        super(rental);
        this.language = language;
    }

    @Override
    public double getRentalCost() {
        return rental.getRentalCost() + 1.5; // Additional cost for translated edition
    }

    @Override
    public String getRentalDescription() {
        return rental.getRentalDescription() + " + Translated to " + language;
    }

    public String getLanguage() { return language; }
}