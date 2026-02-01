package iuh.fit.bai2.model;

/**
 * Concrete implementation for physical books
 */
public class PhysicalBook extends Book {
    private String location;
    private int pageCount;

    public PhysicalBook(String id, String title, String author, String genre, String location, int pageCount) {
        super(id, title, author, genre);
        this.location = location;
        this.pageCount = pageCount;
    }

    @Override
    public String getBookType() {
        return "Physical Book";
    }

    @Override
    public double getRentalPrice() {
        return 5.0; // Base rental price for physical books
    }

    public String getLocation() { return location; }
    public int getPageCount() { return pageCount; }
}