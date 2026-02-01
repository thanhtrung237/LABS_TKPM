package iuh.fit.bai2.factory;

import iuh.fit.bai2.model.Book;

/**
 * Abstract factory for creating books
 */
public abstract class BookFactory {
    public abstract Book createBook(String id, String title, String author, String genre, Object... additionalParams);
    
    public static BookFactory getFactory(String bookType) {
        switch (bookType.toLowerCase()) {
            case "physical":
                return new PhysicalBookFactory();
            case "ebook":
                return new EBookFactory();
            case "audio":
                return new AudioBookFactory();
            default:
                throw new IllegalArgumentException("Unknown book type: " + bookType);
        }
    }
}