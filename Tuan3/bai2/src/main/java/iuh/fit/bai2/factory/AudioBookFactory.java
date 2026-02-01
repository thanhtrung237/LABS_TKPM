package iuh.fit.bai2.factory;

import iuh.fit.bai2.model.Book;
import iuh.fit.bai2.model.AudioBook;

/**
 * Factory for creating audio books
 */
public class AudioBookFactory extends BookFactory {
    @Override
    public Book createBook(String id, String title, String author, String genre, Object... additionalParams) {
        String narrator = additionalParams.length > 0 ? (String) additionalParams[0] : "Unknown Narrator";
        int durationMinutes = additionalParams.length > 1 ? (Integer) additionalParams[1] : 300;
        
        return new AudioBook(id, title, author, genre, narrator, durationMinutes);
    }
}