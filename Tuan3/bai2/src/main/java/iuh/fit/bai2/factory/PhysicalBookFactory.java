package iuh.fit.bai2.factory;

import iuh.fit.bai2.model.Book;
import iuh.fit.bai2.model.PhysicalBook;

/**
 * Factory for creating physical books
 */
public class PhysicalBookFactory extends BookFactory {
    @Override
    public Book createBook(String id, String title, String author, String genre, Object... additionalParams) {
        String location = additionalParams.length > 0 ? (String) additionalParams[0] : "A1-01";
        int pageCount = additionalParams.length > 1 ? (Integer) additionalParams[1] : 200;
        
        return new PhysicalBook(id, title, author, genre, location, pageCount);
    }
}