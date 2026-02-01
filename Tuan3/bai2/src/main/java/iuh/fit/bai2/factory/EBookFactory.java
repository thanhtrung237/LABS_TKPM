package iuh.fit.bai2.factory;

import iuh.fit.bai2.model.Book;
import iuh.fit.bai2.model.EBook;

/**
 * Factory for creating e-books
 */
public class EBookFactory extends BookFactory {
    @Override
    public Book createBook(String id, String title, String author, String genre, Object... additionalParams) {
        String downloadUrl = additionalParams.length > 0 ? (String) additionalParams[0] : "https://library.com/ebooks/" + id;
        double fileSize = additionalParams.length > 1 ? (Double) additionalParams[1] : 5.0;
        
        return new EBook(id, title, author, genre, downloadUrl, fileSize);
    }
}