package iuh.fit.bai2.model;

import java.time.LocalDate;

/**
 * Abstract base class for all book types
 */
public abstract class Book {
    protected String id;
    protected String title;
    protected String author;
    protected String genre;
    protected boolean isAvailable;
    protected LocalDate publishDate;

    public Book(String id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
        this.publishDate = LocalDate.now();
    }

    // Getters and setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public LocalDate getPublishDate() { return publishDate; }

    public abstract String getBookType();
    public abstract double getRentalPrice();

    @Override
    public String toString() {
        return String.format("[%s] %s by %s (%s) - %s", 
            getBookType(), title, author, genre, isAvailable ? "Available" : "Borrowed");
    }
}