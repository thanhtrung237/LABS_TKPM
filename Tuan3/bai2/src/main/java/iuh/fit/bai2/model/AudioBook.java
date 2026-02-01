package iuh.fit.bai2.model;

/**
 * Concrete implementation for audio books
 */
public class AudioBook extends Book {
    private String narrator;
    private int durationMinutes;

    public AudioBook(String id, String title, String author, String genre, String narrator, int durationMinutes) {
        super(id, title, author, genre);
        this.narrator = narrator;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String getBookType() {
        return "Audio Book";
    }

    @Override
    public double getRentalPrice() {
        return 7.0; // Base rental price for audio books
    }

    public String getNarrator() { return narrator; }
    public int getDurationMinutes() { return durationMinutes; }
}