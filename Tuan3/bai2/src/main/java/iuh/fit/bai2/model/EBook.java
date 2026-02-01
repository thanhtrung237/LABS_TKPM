package iuh.fit.bai2.model;

/**
 * Concrete implementation for electronic books
 */
public class EBook extends Book {
    private String downloadUrl;
    private double fileSize; // in MB

    public EBook(String id, String title, String author, String genre, String downloadUrl, double fileSize) {
        super(id, title, author, genre);
        this.downloadUrl = downloadUrl;
        this.fileSize = fileSize;
    }

    @Override
    public String getBookType() {
        return "E-Book";
    }

    @Override
    public double getRentalPrice() {
        return 3.0; // Base rental price for e-books
    }

    public String getDownloadUrl() { return downloadUrl; }
    public double getFileSize() { return fileSize; }
}